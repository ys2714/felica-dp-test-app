package com.zebra.znfc_helper.nfc_type_B

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NFCService {

    protected var nfcAdapter: NfcAdapter? = null

    protected val backgroundScope = CoroutineScope(Dispatchers.IO + Job())
    protected val foregroundScope = CoroutineScope(Dispatchers.Main + Job())

    fun prepare(activity: Activity) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
    }

    /***
     * Flags:
     * FLAG_READER_NFC_A
     * FLAG_READER_NFC_B
     * FLAG_READER_NFC_F
     * FLAG_READER_NFC_V
     * FLAG_READER_NFC_BARCODE
     * FLAG_READER_SKIP_NDEF_CHECK
     * FLAG_READER_NO_PLATFORM_SOUNDS
     * */
    fun prepareForReadJPDL(activity: Activity, onTagDetected: (Tag) -> Unit) {
        nfcAdapter?.enableReaderMode(
            activity,
            { tag ->
                onTagDetected(tag)
            },
            NfcAdapter.FLAG_READER_NFC_B or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            Bundle().apply {
                putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 1300)
            }
        )
    }

    fun prepareForReadTypeA(activity: Activity, onTagDetected: (Tag) -> Unit) {
        nfcAdapter?.enableReaderMode(
            activity,
            { tag ->
                onTagDetected(tag)
            },
            NfcAdapter.FLAG_READER_NFC_A,
            Bundle()
        )
    }

    fun prepareForReadTypeB(activity: Activity, onTagDetected: (Tag) -> Unit) {
        nfcAdapter?.enableReaderMode(
            activity,
            { tag ->
                onTagDetected(tag)
            },
            NfcAdapter.FLAG_READER_NFC_B,
            Bundle()
        )
    }

    fun disable(activity: Activity) {
        nfcAdapter?.disableReaderMode(activity)
    }
}