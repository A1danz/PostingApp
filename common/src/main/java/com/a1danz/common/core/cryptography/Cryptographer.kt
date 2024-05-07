package com.a1danz.common.core.cryptography


import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Cryptographer {
    private val KEY = "dsj2u48fk4skfnj4"
    private val ALGORITHM = "AES"
    private val TRANSFORMATION = "AES/ECB/PKCS5Padding"

    private val CIPHER = Cipher.getInstance(TRANSFORMATION).apply {
        val secretKey = SecretKeySpec(KEY.toByteArray(), ALGORITHM)
        init(Cipher.ENCRYPT_MODE, secretKey)
    }

    fun encrypt(text: String): String {
        val encryptedBytes = CIPHER.doFinal(text.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }
}