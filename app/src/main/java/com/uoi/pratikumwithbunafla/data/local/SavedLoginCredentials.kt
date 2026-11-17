package com.uoi.pratikumwithbunafla.data.local

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import org.json.JSONObject
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

data class SavedLoginCredentials(
    val username: String,
    val password: String,
    val role: String
)

object SavedLoginCredentialsStore {
    private const val PREFERENCES_NAME = "smart_waste_saved_login"
    private const val KEY_ALIAS = "smart_waste_saved_login_v1"
    private const val IV_KEY = "iv"
    private const val CIPHER_TEXT_KEY = "cipher_text"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private val lock = Any()

    fun load(context: Context): SavedLoginCredentials? = synchronized(lock) {
        val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val encodedIv = preferences.getString(IV_KEY, null) ?: return null
        val encodedCipherText = preferences.getString(CIPHER_TEXT_KEY, null) ?: return null

        runCatching {
            val iv = Base64.decode(encodedIv, Base64.NO_WRAP)
            val cipherText = Base64.decode(encodedCipherText, Base64.NO_WRAP)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(128, iv))
            cipher.updateAAD(context.packageName.toByteArray(Charsets.UTF_8))
            val json = JSONObject(String(cipher.doFinal(cipherText), Charsets.UTF_8))
            SavedLoginCredentials(
                username = json.getString("username"),
                password = json.getString("password"),
                role = json.getString("role")
            ).takeIf { it.username.isNotBlank() && it.password.isNotBlank() }
        }.getOrElse {
            preferences.edit().remove(IV_KEY).remove(CIPHER_TEXT_KEY).apply()
            deleteKey()
            null
        }
    }

    fun save(context: Context, credentials: SavedLoginCredentials) = synchronized(lock) {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        cipher.updateAAD(context.packageName.toByteArray(Charsets.UTF_8))
        val payload = JSONObject()
            .put("username", credentials.username)
            .put("password", credentials.password)
            .put("role", credentials.role)
            .toString()
            .toByteArray(Charsets.UTF_8)
        val cipherText = cipher.doFinal(payload)
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
            .putString(IV_KEY, Base64.encodeToString(cipher.iv, Base64.NO_WRAP))
            .putString(CIPHER_TEXT_KEY, Base64.encodeToString(cipherText, Base64.NO_WRAP))
            .apply()
    }

    fun clear(context: Context) {
        synchronized(lock) {
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .remove(IV_KEY)
                .remove(CIPHER_TEXT_KEY)
                .apply()
            deleteKey()
        }
    }

    private fun deleteKey() {
        runCatching {
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
            if (keyStore.containsAlias(KEY_ALIAS)) keyStore.deleteEntry(KEY_ALIAS)
        }
    }

    private fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        (keyStore.getKey(KEY_ALIAS, null) as? SecretKey)?.let { return it }

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(true)
                .setUserAuthenticationRequired(false)
                .build()
        )
        return keyGenerator.generateKey()
    }
}
