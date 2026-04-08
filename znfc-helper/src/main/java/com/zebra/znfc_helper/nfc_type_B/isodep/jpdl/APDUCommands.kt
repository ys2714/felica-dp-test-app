package com.zebra.znfc_helper.nfc_type_B.isodep.jpdl

object APDUCommands {

    // --- 1. Select the main application (Master File) ---
    fun selectMasterFile(): ByteArray {
        return apdu("00A40000")
    }

    // --- 2. Verify PIN 1 ---
    // This is required to read common data like name, birthdate, address, etc.
    fun verifyPIN1(pin1: String): ByteArray {
        val Lc = "04"
        return apdu("00200081" + Lc + pin1.toByteArray().toHex())
    }

    // --- 3. Read Common Data File (EF01) ---
    fun selectCommonData(): ByteArray {
        return apdu("00A4020C022F01")
    }

    fun readCommonDataTLV(): ByteArray {
        // the data length should parse from selectCommonData response.
        val dataLength = "11" //17 bytes to hex
        return apdu("00B00000" + dataLength)
    }

    // --- 4. Verify PIN 2 ---
    // This is required to read sensitive data like the photo.
    fun verifyPIN2(pin2: String): ByteArray {
        val Lc = "04"
        return apdu("00200082" + Lc + pin2.toByteArray().toHex())
    }

    // --- 5. Read Face Photo File (EF11) ---
    fun selectPhotoData(): ByteArray {
        return apdu("00A4020C020011")
    }

    fun readPhotoData(): ByteArray {
        // the data length should parse from selectPhotoData response.
        // This contains the JPEG image data of the cardholder's photo.
        val dataLength = "200"
        return apdu("00B00000" + dataLength)
    }

    // Expected response ends with 9000 (Success)
    val successSuffixCode = "9000"

    fun decodeResponse(response: ByteArray): String {
        return response.toHex()
    }

    fun isResponseSuccess(response: ByteArray): Boolean {
        if (response.toHex().endsWith(successSuffixCode)) {
            return true
        }
        return false
    }

    fun decodeCommonDataTLV(fullResponse: ByteArray): DriverLicenseCommonData {
        // Extract the data payload by removing the last two status bytes.
        val commonDataPayload: ByteArray = fullResponse.copyOfRange(0, fullResponse.size - 2)
        return TLVParser.decode(commonDataPayload)
    }

    // --- Helper functions ---

    private fun apdu(command: String): ByteArray {
        return command.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    }

    private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }

    private fun ByteArray.endsWith(hexSuffix: String): Boolean {
        if (this.size < hexSuffix.length / 2) return false
        val suffixBytes = apdu(hexSuffix)
        return this.takeLast(suffixBytes.size).toByteArray().contentEquals(suffixBytes)
    }
}