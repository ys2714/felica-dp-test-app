package com.zebra.znfc_helper.nfc_type_B.isodep.jpdl

import android.nfc.Tag
import android.nfc.tech.IsoDep
import java.io.IOException

object DriverLicenseReader {

    fun readJPDriverLicense(tag: Tag, pin1: String = "1234", pin2: String = "1234", completion: (String?, DriverLicenseCommonData?) -> Unit) {
        val isoDep = IsoDep.get(tag)
        try {
            isoDep.connect()

            val selectMFResponse = isoDep.transceive(APDUCommands.selectMasterFile())
            if (!APDUCommands.isResponseSuccess(selectMFResponse)) {
                val message = "SELECT MF FAILED, RESPONSE: $selectMFResponse"
                completion(message, null)
                return
            }

            val verifyPin1Response = isoDep.transceive(APDUCommands.verifyPIN1(pin1))
            if (!APDUCommands.isResponseSuccess(verifyPin1Response)) {
                val message = "PIN 1 VERIFICATION FAILED, RESPONSE: $verifyPin1Response"
                completion(message, null)
                return
            }

            isoDep.transceive(APDUCommands.selectCommonData())
            val commonDataResponse = isoDep.transceive(APDUCommands.readCommonDataTLV())
            if (!APDUCommands.isResponseSuccess(commonDataResponse)) {
                val message = "READ COMMON DATA FAILED, RESPONSE: $commonDataResponse"
                completion(message, null)
                return
            }

            val licenseObject = APDUCommands.decodeCommonDataTLV(commonDataResponse)
            //completion(decoded.expireDate.toString(), null)
            completion(null, licenseObject)

        } catch (e: IOException) {
            println("Error communicating with NFC tag: ${e.message}")
            completion(e.message ?: "Unknown Error", null)
        } finally {
            isoDep.close()
        }
    }
}