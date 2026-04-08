package com.zebra.felicadptest

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zebra.znfc_helper.nfc_type_B.NFCService
import com.zebra.znfc_helper.nfc_type_B.isodep.jpdl.DriverLicenseReader
import kotlinx.coroutines.launch

class JPDLViewModel: ViewModel() {

    var version: MutableState<String> = mutableStateOf("")
    var issueDate: MutableState<String> = mutableStateOf("")
    var expireDate: MutableState<String> = mutableStateOf("")
    var manufacturerID: MutableState<String> = mutableStateOf("")
    var encryptionID: MutableState<String> = mutableStateOf("")

    private val nfcService = NFCService()

    fun handleOnCreate(activity: Activity) {
        nfcService.prepare(activity)
    }

    fun handleOnResume(activity: Activity) {
        nfcService.prepareForReadJPDL(activity) { tag ->
            DriverLicenseReader.readJPDriverLicense(tag, "1234", "1234") { error, data ->
                if (error != null) {
                    // this run on main thread
                    viewModelScope.launch {
                        Toast.makeText(activity, "ERROR: ${error}", Toast.LENGTH_LONG).show()
                    }
                }
                if (data != null) {
                    this.version.value = data.version
                    this.issueDate.value = data.issueDate.toString()
                    this.expireDate.value = data.expireDate.toString()
                    this.manufacturerID.value = data.manufacturerID.toString()
                    this.encryptionID.value = data.encryptionID.toString()
                    // this run on main thread
                    viewModelScope.launch {
                        Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    fun handleOnPause(activity: Activity) {
        nfcService.disable(activity)
    }

    fun handleOnDestroy(activity: Activity) {

    }

    fun reset() {
        this.version.value = ""
        this.issueDate.value = ""
        this.expireDate.value = ""
        this.manufacturerID.value = ""
        this.encryptionID.value = ""
    }
}