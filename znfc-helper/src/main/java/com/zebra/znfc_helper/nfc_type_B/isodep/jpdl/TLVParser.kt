package com.zebra.znfc_helper.nfc_type_B.isodep.jpdl

import java.io.IOException
import java.nio.charset.Charset
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates

class JapaneseDriverLicense(
    val specification: String,
    val issuer: String,
    val name: String,
    val kana: String,
    val birthDate: String,
    val address: String,
    val issueDate: String,
    val refNumber: String,
    val colorClass: String,
    val expiryDate: String,
    val licenseNumber: String
) {}

class DriverLicenseCommonData(
    var version: String = "",
    var issueDate: Date = Date(),
    var expireDate: Date = Date(),
    var manufacturerID: Byte = 0x00.toByte(),
    var encryptionID: Byte = 0x00.toByte()
) {}

class DriverLicenseTagData(
    var tag: Int = 0,
    var value: ByteArray
)

object TLVParser {

    private var pos = 0
    private var size by Delegates.notNull<Int>()
    private var tagsize = 1

    /**
     * Parses the TLV-encoded common data from a Japanese driver's license.
     * This data is the payload from a READ BINARY command on MF/DF1/EF01.
     *
     * @param data The raw ByteArray payload (with status bytes already removed).
     * @return A [JapaneseDriverLicense] object populated with the decoded data.
     */
    private fun parseCommonData(data: ByteArray): JapaneseDriverLicense {
        // This map defines the tags for the fields in the common data file.
        val tagMap = mapOf(
            0x0101 to "specification",
            0x0102 to "issuer",
            0x0201 to "name",
            0x0202 to "kana",
            0x0203 to "birthDate",
            0x0204 to "address",
            0x0205 to "issueDate",
            0x0206 to "refNumber",     // 照会番号 (Shoukai Bangou)
            0x0207 to "colorClass",    // 優良区分 (Yuuryou Kubun)
            0x0208 to "expiryDate",
            0x020D to "licenseNumber"
        )

        val decodedData = mutableMapOf<String, String>()
        var index = 0 // Start from the beginning of the data

        // Loop through the byte array as long as there's enough space
        // for a minimal TLV record (Tag=2 bytes, Length=1 byte).
        while (index < data.size - 2) {
            // Read the 2-byte Tag (Big-Endian)
            val tag = ((data[index].toInt() and 0xFF) shl 8) or (data[index + 1].toInt() and 0xFF)
            index += 2

            // Read the 1-byte Length
            val length = data[index].toInt() and 0xFF
            index += 1

            // Basic sanity check to prevent crash on malformed data
            if (index + length > data.size) {
                System.err.println("Error: Malformed TLV data. Aborting parse.")
                break
            }

            // Read the Value
            val value = data.copyOfRange(index, index + length)
            index += length

            // Find the field name from our map and store the decoded value
            tagMap[tag]?.let { fieldName ->
                // The official encoding is Shift_JIS. Use Charset.forName("Shift_JIS").
                // Using UTF-8 as a fallback for testing if needed.
                try {
                    decodedData[fieldName] = String(value, Charset.forName("Shift_JIS"))
                } catch (e: Exception) {
                    System.err.println("Failed to decode field '$fieldName' with Shift_JIS. Error: ${e.message}")
                    decodedData[fieldName] = "DECODING_ERROR"
                }
            }
        }

        return JapaneseDriverLicense(
            specification = decodedData["specification"] ?: "",
            issuer = decodedData["issuer"] ?: "",
            name = decodedData["name"] ?: "",
            kana = decodedData["kana"] ?: "",
            birthDate = decodedData["birthDate"] ?: "",
            address = decodedData["address"] ?: "",
            issueDate = decodedData["issueDate"] ?: "",
            refNumber = decodedData["refNumber"] ?: "",
            colorClass = decodedData["colorClass"] ?: "",
            expiryDate = decodedData["expiryDate"] ?: "",
            licenseNumber = decodedData["licenseNumber"] ?: ""
        )
    }

    private fun readObject(encoded: ByteArray): DriverLicenseTagData? {
        if (this.pos >= this.size) {
            return null
        } else {
            var tag = encoded[this.pos++].toInt() and 255
            if (tag == 1) {
                tag = tag shl 8 or (encoded[this.pos++].toInt() and 255)
            } else if (this.tagsize == 2) {
                tag = tag shl 8 or (encoded[this.pos++].toInt() and 255)
            }

            if (tag == 0) {
                return null
            } else if (this.pos >= this.size) {
                return null
            } else {
                var length = encoded[this.pos++].toInt() and 255
                if ((tag == 255 || tag == 65535) && length == 255) {
                    return null
                } else {
                    if ((length and 128) != 0) {
                        val length_of_length = length and 127
                        if (length_of_length != 2) {
                            throw IOException("unexpected length of length: " + length_of_length)
                        }

                        length = 0

                        for (i in 0..<length_of_length) {
                            length = (length shl 8) + (encoded[this.pos++].toInt() and 255)
                        }
                    }

                    val value = Arrays.copyOfRange(encoded, this.pos, this.pos + length)
                    this.pos += length
                    return DriverLicenseTagData(tag, value)
                }
            }
        }
    }

    fun decode(encoded: ByteArray): DriverLicenseCommonData {
        this.pos = 0
        this.size = encoded.size

        var result = DriverLicenseCommonData()
        val df = SimpleDateFormat("yyyyMMdd", Locale.US)

        var obj: DriverLicenseTagData?
        while ((this.readObject(encoded).also { obj = it }) != null) {
            val tag: Int = obj!!.tag
            val data: ByteArray = obj.value
            when (tag) {
                69 -> {
                    result.version = String(data, 0, 3)
                    var date = String.format("%02x%02x%02x%02x", data[3], data[4], data[5], data[6])

                    try {
                        result.issueDate = df.parse(date)
                    } catch (var9: ParseException) {
                        throw IOException("cannot parse issueDate: " + date)
                    }

                    date = String.format("%02x%02x%02x%02x", data[7], data[8], data[9], data[10])

                    try {
                        result.expireDate = df.parse(date)
                        break
                    } catch (var8: ParseException) {
                        throw IOException("cannot parse expireDate: " + date)
                    }
                    result.manufacturerID = data[0]
                    result.encryptionID = data[1]
                }

                70 -> {
                    result.manufacturerID = data[0]
                    result.encryptionID = data[1]
                }
            }
        }

        return result
    }
}
