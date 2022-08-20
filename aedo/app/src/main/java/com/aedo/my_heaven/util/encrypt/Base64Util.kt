package com.aedo.my_heaven.util.encrypt

import android.util.Base64
import java.nio.charset.StandardCharsets

object Base64Util {
    fun encode(text: String): String? {
        val data = text.toByteArray()
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    fun encode(data: ByteArray?): String? {
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    fun decode(text: String?): String {
        return String(Base64.decode(text, Base64.DEFAULT), StandardCharsets.UTF_8)
    }
}