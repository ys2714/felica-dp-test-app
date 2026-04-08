package com.zebra.znfc_helper.utils

import java.io.ByteArrayOutputStream

object Hex {
    @JvmOverloads
    fun encode(bytes: ByteArray, sep: String? = ""): String {
        val sb = StringBuilder()

        for (i in bytes.indices) {
            var s = sep
            if (i + 1 == bytes.size) {
                s = ""
            }

            sb.append(String.format("%02X%s", bytes[i], s))
        }

        return sb.toString()
    }
}