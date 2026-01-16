package com.zebra.felicadptest

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcF
import android.os.Bundle

class FelicaReaderService: FelicaService(), NfcAdapter.ReaderCallback {

    override fun onTagDiscovered(tag: Tag?) {
        tag?.let {
            val nfcF = NfcF.get(it)
            sendCommand(nfcF, FelicaCommands.DPTest.positiveEncodingCommand) {
                response ->
            }
        }
    }

    fun prepareReaderMode(activity: Activity) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        nfcAdapter?.enableReaderMode(
            activity,
            this,
            NfcAdapter.FLAG_READER_NFC_F or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            Bundle.EMPTY)
    }

    fun disableReaderMode(activity: Activity) {
        nfcAdapter?.disableReaderMode(activity)
    }


}