package com.pointlessapps.mypremiummobile.local.datasource.auth

import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.google.gson.Gson
import com.pointlessapps.mypremiummobile.datasource.auth.CredentialsStore
import com.pointlessapps.mypremiummobile.datasource.auth.dto.Credentials
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import javax.crypto.Cipher

internal class CredentialsStoreImpl(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences,
    private val keyAlias: String,
) : CredentialsStore {

    private var credentials: Credentials? = null
    private val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).also {
        it.load(null)
    }

    private fun generateKeyPair() {
        KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_RSA,
            KEYSTORE_PROVIDER,
        ).apply {
            initialize(
                KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                ).setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .build(),
            )
        }.generateKeyPair()
    }

    override fun setCredentials(login: String, password: String) {
        if (!keyStore.containsAlias(keyAlias)) {
            generateKeyPair()
        }

        credentials = Credentials(
            username = login,
            password = password,
        )

        val result = encrypt(gson.toJson(credentials))

        sharedPreferences.edit().apply {
            putString(DATA_KEY, result)
        }.apply()
    }

    override fun getCredentials(): Credentials? {
        if (credentials != null) {
            return credentials
        }

        if (!keyStore.containsAlias(keyAlias)) {
            generateKeyPair()

            return null
        }

        val data = sharedPreferences.getString(DATA_KEY, null) ?: return null

        return gson.fromJson(decrypt(data), Credentials::class.java)
    }

    override fun clear() = sharedPreferences.edit().apply { clear() }.apply()

    private fun encrypt(data: String): String {
        val encryptKey = keyStore.getCertificate(keyAlias).publicKey

        val result = Cipher.getInstance(RSA_CIPHER).apply {
            init(Cipher.ENCRYPT_MODE, encryptKey)
        }.doFinal(data.toByteArray())

        return Base64.encodeToString(result, Base64.DEFAULT)
    }

    private fun decrypt(data: String): String {
        val decryptKey = keyStore.getKey(keyAlias, null) as PrivateKey

        val result = Cipher.getInstance(RSA_CIPHER).apply {
            init(Cipher.DECRYPT_MODE, decryptKey)
        }.doFinal(Base64.decode(data, Base64.DEFAULT))

        return String(result)
    }

    companion object {
        const val SHARED_PREFERENCES_KEY = "credentials_store"

        private const val DATA_KEY = "data"

        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val RSA_CIPHER = "RSA/ECB/PKCS1Padding"
    }
}
