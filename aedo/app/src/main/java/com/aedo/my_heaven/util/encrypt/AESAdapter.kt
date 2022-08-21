package com.aedo.my_heaven.util.encrypt

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.Key
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESAdapter {
    private const val encMethod = "AES/CBC/PKCS5Padding"

    private fun getAESKey(secretKey: String): Key {
        val keySpec: Key
        val keyBytes = ByteArray(16)
        val b = secretKey.toByteArray(StandardCharsets.UTF_8)
        var length = b.size
        if (length > keyBytes.size) {
            length = keyBytes.size
        }
        System.arraycopy(b, 0, keyBytes, 0, length)
        keySpec = SecretKeySpec(keyBytes, "AES")
        return keySpec
    }

    fun encAES(iv: String, key: String, text: String?): String? {
        var encStr = ""
        val keySpec = getAESKey(key)
        val c: Cipher
        if (text == null) {
            return text
        }
        try {
            c = Cipher.getInstance(encMethod)
            c.init(
                Cipher.ENCRYPT_MODE,
                keySpec,
                IvParameterSpec(iv.toByteArray(StandardCharsets.UTF_8))
            )
            val encrypted = c.doFinal(text.toByteArray(StandardCharsets.UTF_8))
            encStr = String(Base64.encode(encrypted, Base64.DEFAULT))
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: NoSuchPaddingException) {
        } catch (e: InvalidKeyException) {
        } catch (e: InvalidAlgorithmParameterException) {
        } catch (e: IllegalBlockSizeException) {
        } catch (e: BadPaddingException) {
        }
        if (encStr.endsWith("\n")) {
            encStr = encStr.replace("\n", "")
        }
        return encStr
    }

    fun decAES(iv: String, key: String, encStr: String?): String? {
        var decStr = ""
        val keySpec = getAESKey(key)
        val c: Cipher
        if (encStr == null) {
            return encStr
        }
        try {
            c = Cipher.getInstance(encMethod)
            c.init(
                Cipher.DECRYPT_MODE,
                keySpec,
                IvParameterSpec(iv.toByteArray(StandardCharsets.UTF_8))
            )
            val byteStr = Base64.decode(encStr.toByteArray(StandardCharsets.UTF_8), Base64.DEFAULT)
            decStr = String(c.doFinal(byteStr), StandardCharsets.UTF_8)
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: NoSuchPaddingException) {
        } catch (e: InvalidKeyException) {
        } catch (e: InvalidAlgorithmParameterException) {
        } catch (e: IllegalBlockSizeException) {
        } catch (e: BadPaddingException) {
        }
        return decStr
    }
}