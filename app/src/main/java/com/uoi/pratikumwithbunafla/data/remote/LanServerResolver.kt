package com.uoi.pratikumwithbunafla.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.URL
import java.nio.ByteBuffer
import java.util.Collections
import java.util.concurrent.atomic.AtomicReference

/** Menemukan server PHP Smart Waste saat alamat Wi-Fi laptop berubah. */
internal object LanServerResolver {
    private const val SERVER_PORT = 8080
    private const val HEALTH_PATH = "/Waste2/backend-php/index.php/health"
    private const val CACHE_MILLIS = 15_000L
    private const val PROBE_TIMEOUT_MILLIS = 650

    private val mutex = Mutex()
    @Volatile private var verifiedHost: String? = null
    @Volatile private var verifiedAtMillis = 0L

    suspend fun resolveHost(configuredApiUrl: String): String = withContext(Dispatchers.IO) {
        mutex.withLock {
            val now = System.currentTimeMillis()
            verifiedHost?.takeIf { now - verifiedAtMillis < CACHE_MILLIS }?.let { return@withLock it }

            val configuredHost = runCatching { URL(configuredApiUrl).host }.getOrNull()
            val knownHosts = listOfNotNull(verifiedHost, configuredHost, "10.0.2.2").distinct()
            knownHosts.firstOrNull { isSmartWasteServer(it) }?.let { return@withLock remember(it) }

            val discoveredHost = scanLocalSubnet()
                ?: throw IOException(
                    "Server Smart Waste tidak ditemukan. Sambungkan HP dan laptop ke Wi-Fi yang sama, lalu pastikan Apache XAMPP aktif di port 8080."
                )
            remember(discoveredHost)
        }
    }

    fun apiBaseUrl(host: String): String = "http://$host:$SERVER_PORT/Waste2/backend-php/index.php"

    fun markUnreachable(host: String) {
        if (verifiedHost == host) verifiedAtMillis = 0L
    }

    private fun remember(host: String): String {
        verifiedHost = host
        verifiedAtMillis = System.currentTimeMillis()
        return host
    }

    private fun isSmartWasteServer(host: String): Boolean = runCatching {
        val connection = (URL("http://$host:$SERVER_PORT$HEALTH_PATH").openConnection() as HttpURLConnection).apply {
            connectTimeout = PROBE_TIMEOUT_MILLIS
            readTimeout = PROBE_TIMEOUT_MILLIS
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Cache-Control", "no-cache")
            setRequestProperty("Connection", "close")
        }
        try {
            if (connection.responseCode !in 200..299) return false
            val body = connection.inputStream.bufferedReader().use { it.readText() }
            JSONObject(body).optString("status") == "ok" &&
                JSONObject(body).optString("service") == "Smart Waste REST API"
        } finally {
            connection.disconnect()
        }
    }.getOrDefault(false)

    private suspend fun scanLocalSubnet(): String? = coroutineScope {
        val hosts = localIpv4Candidates()
        if (hosts.isEmpty()) return@coroutineScope null

        val semaphore = Semaphore(32)
        val found = AtomicReference<String?>(null)
        hosts.map { host ->
            async {
                if (found.get() == null) {
                    semaphore.withPermit {
                        if (found.get() == null && isSmartWasteServer(host)) {
                            found.compareAndSet(null, host)
                        }
                    }
                }
            }
        }.awaitAll()
        found.get()
    }

    private fun localIpv4Candidates(): List<String> = runCatching {
        val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
        val candidates = linkedSetOf<String>()

        interfaces
            .filter { network ->
                network.isUp && !network.isLoopback &&
                    network.name.contains(Regex("wlan|wifi|eth", RegexOption.IGNORE_CASE))
            }
            .forEach { network ->
                network.interfaceAddresses.forEach addressLoop@{ interfaceAddress ->
                    val address = interfaceAddress.address as? Inet4Address ?: return@addressLoop
                    val prefixLength = interfaceAddress.networkPrefixLength.toInt()
                    if (prefixLength !in 22..30) return@addressLoop

                    val localAddress = ByteBuffer.wrap(address.address).int
                    val mask = -1 shl (32 - prefixLength)
                    val networkAddress = localAddress and mask
                    val addressCount = 1 shl (32 - prefixLength)
                    for (offset in 1 until addressCount - 1) {
                        val candidate = networkAddress or offset
                        if (candidate != localAddress) candidates += toIpv4(candidate)
                    }
                }
            }
        candidates.toList()
    }.getOrDefault(emptyList())

    private fun toIpv4(address: Int): String = listOf(
        (address ushr 24) and 0xff,
        (address ushr 16) and 0xff,
        (address ushr 8) and 0xff,
        address and 0xff
    ).joinToString(".")
}
