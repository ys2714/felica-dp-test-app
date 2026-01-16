package com.zebra.felicadptest

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/*
* <uses-permission android:name="android.permission.NFC" />
* <uses-feature android:name="android.hardware.nfc" android:required="true" />
* */
class FelicaViewModel: ViewModel() {

    // Outlets
    val codingPolarityText = mutableStateOf<String>("")
    val commandTypeText = mutableStateOf<String>("")
    val commandText = mutableStateOf<String>("")
    val manufacturerText = mutableStateOf<String>("")
    val responseText = mutableStateOf<String>("")

    private var alert: AlertDialog? = null
    private val felicaService = FelicaService()

    fun handleOnCreate(activity: Activity) {
        felicaService.prepare(activity)
        // felicaService.prepareReaderMode(activity)
        reset()
    }

    fun handleOnResume(activity: Activity) {
        felicaService.enable(activity)
    }

    fun handleOnPause(activity: Activity) {
        felicaService.disable(activity)
    }

    fun handleOnNewIntent(activity: Activity, intent: Intent) {
        felicaService.onTagDetected(intent) { tag ->
            manufacturerText.value = tag.manufacturer.toHexString()
            // showDialog(activity, "DP test", "send current command") {
                felicaService.sendCurrentCommand(tag) { response ->
                    responseText.value = response
                    if (felicaService.checkDPTestResponse(response.toByteArray())) {
                        Toast.makeText(
                            activity,
                            "PASS",
                            Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(
                            activity,
                            "FAIL",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            // }
        }
    }

//    fun setCodingPolarity(polarity: FelicaService.CodingPolarity) {
//        felicaService.config(polarity)
//        codingPolarityText.value = polarity.name
//        manufacturerText.value = ""
//        responseText.value = ""
//    }

    fun setCommandType(commandType: FelicaService.CommandType) {
        commandTypeText.value = commandType.name
        commandText.value = felicaService.config(commandType)
        manufacturerText.value = ""
        responseText.value = ""

        //felicaService.sendCommand(felicaService.nfcAdapter!!.tag!!, felicaService.currentCommand)
    }

    fun sendCommand(commandType: FelicaService.CommandType) {

    }

    fun reset() {
        // setCodingPolarity(FelicaService.CodingPolarity.Keep)
        setCommandType(FelicaService.CommandType.Polling)
    }

    fun showDialog(context: Context, title: String, message: String, onOKPressed: () -> Unit) {
        alert?.dismiss()
        alert = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                onOKPressed()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}
