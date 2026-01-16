package com.zebra.felicadptest

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.NfcF
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

open class FelicaService {

//    enum class CodingPolarity {
//        Positive,
//        Negative,
//        Keep
//    }

    enum class CommandType {
        Positive,
        Negative,
        Polling,
        Command1,
        Command3,
        Command5,
        Command7,
        Command9,
        Command11
    }

    //private var codingPolarity = CodingPolarity.Keep
    protected var nfcAdapter: NfcAdapter? = null
    protected lateinit var pendingIntent: PendingIntent

    protected val backgroundScope = CoroutineScope(Dispatchers.IO + Job())
    protected val foregroundScope = CoroutineScope(Dispatchers.Main + Job())

    val currentCommand: ByteArray
        get() {
            return when (currentCommandType) {
                CommandType.Positive -> FelicaCommands.DPTest.positiveEncodingCommand
                CommandType.Negative -> FelicaCommands.DPTest.negativeEncodingCommand
                CommandType.Polling -> FelicaCommands.pollingCommand
                CommandType.Command1 -> FelicaCommands.DPTest.command1
                CommandType.Command3 -> FelicaCommands.DPTest.command3
                CommandType.Command5 -> FelicaCommands.DPTest.command5
                CommandType.Command7 -> FelicaCommands.DPTest.command7
                CommandType.Command9 -> FelicaCommands.DPTest.command9
                CommandType.Command11 -> FelicaCommands.DPTest.command11
            }
        }

    protected var currentCommandType: CommandType = CommandType.Command1

    fun prepare(activity: Activity) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        val intent = Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_MUTABLE)
    }

    fun enable(activity: Activity) {
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, null, null)
    }

    fun disable(activity: Activity) {
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    fun config(commandType: CommandType) : String {
        this.currentCommandType = commandType
        return currentCommand.toHexString()
    }

    fun sendCurrentCommand(nfcF: NfcF, completion: (String) -> Unit) {
        sendCommand(nfcF, currentCommand) { response ->
            completion(response)
        }
    }

    fun sendCommand(nfcF: NfcF, command: ByteArray, completion: (String) -> Unit) {
        backgroundScope.launch {
            try {
                nfcF.connect()
                val response = nfcF.transceive(currentCommand)
                nfcF.close()
                Log.d("NfcF", "Sending command: ${currentCommand.toHexString()}")
                Log.d("NfcF", "Response: ${response.toHexString()}")
                foregroundScope.launch {
                    completion(response.toHexString())
                }
            } catch (e: IOException) {
                nfcF.close()
                Log.e("NfcF", "Error communicating with tag", e)
                foregroundScope.launch {
                    completion(e.message ?: "")
                }
            } catch (e: SecurityException) {
                nfcF.close()
                Log.e("NfcF", "Error communicating with tag", e)
                foregroundScope.launch {
                    completion(e.message ?: "")
                }
            } catch (e: TagLostException) {
                nfcF.close()
                Log.e("NfcF", "Error communicating with tag", e)
                foregroundScope.launch {
                    completion(e.message ?: "")
                }
            } catch (e: Exception) {
                nfcF.close()
                Log.e("NfcF", "Error communicating with tag", e)
                foregroundScope.launch {
                    completion(e.message ?: "")
                }
            }
        }
    }

    fun onTagDetected(intent: Intent, completion: (NfcF) -> Unit) {
        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            val nfcF = NfcF.get(tag)
            if (nfcF != null) {
                // this is Felica
                completion(nfcF)
//                sendCommand(nfcF, currentCommand) {
//                        command, response ->
//                    completion(command, response)
//                }

//                when (codingPolarity) {
//                    CodingPolarity.Positive -> {
//                        setPositiveEncoding(nfcF) { _, _ ->
//                            sendCommand(nfcF, currentCommand) {
//                                command, response ->
//                                completion(command, response)
//                            }
//                        }
//                    }
//                    CodingPolarity.Negative -> {
//                        setNegativeEncoding(nfcF) { _, _ ->
//                            sendCommand(nfcF, currentCommand) {
//                                command, response ->
//                                completion(command, response)
//                            }
//                        }
//                    }
//                    CodingPolarity.Keep -> {
//                        sendCommand(nfcF, currentCommand) {
//                            command, response ->
//                            completion(command, response)
//                        }
//                    }
//                }
            }
        }
    }

    fun setPositiveEncoding(nfcF: NfcF, completion: (String) -> Unit) {
        sendCommand(nfcF, FelicaCommands.DPTest.positiveEncodingCommand) {
            response ->
            completion(response)
        }
    }

    fun setNegativeEncoding(nfcF: NfcF, completion: (String) -> Unit) {
        sendCommand(nfcF, FelicaCommands.DPTest.negativeEncodingCommand) {
            response ->
            completion(response)
        }
    }

    fun checkDPTestResponse(response: ByteArray): Boolean {
        if (currentCommand.contentEquals(FelicaCommands.DPTest.command1)) {
            return response.contentEquals(FelicaResponse.DPTest.response2)
        }
        if (currentCommand.contentEquals(FelicaCommands.DPTest.command3)) {
            return response.contentEquals(FelicaResponse.DPTest.response4)
        }
        if (currentCommand.contentEquals(FelicaCommands.DPTest.command5)) {
            return response.contentEquals(FelicaResponse.DPTest.response6)
        }
        if (currentCommand.contentEquals(FelicaCommands.DPTest.command7)) {
            return response.contentEquals(FelicaResponse.DPTest.response8)
        }
        if (currentCommand.contentEquals(FelicaCommands.DPTest.command9)) {
            return response.contentEquals(FelicaResponse.DPTest.response10)
        }
        if (currentCommand.contentEquals(FelicaCommands.DPTest.command11)) {
            return response.contentEquals(FelicaResponse.DPTest.response12)
        }
        return true
    }
}

// A Kotlin extension function to add functionality to the ByteArray class
fun ByteArray.toHexString(): String =
    joinToString(separator = " ") { eachByte -> "%02x".format(eachByte) }