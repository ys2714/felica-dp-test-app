package com.zebra.felicadptest

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zebra.felicadptest.ZebraBaseComponentActivity
import com.zebra.felicadptest.ui.theme.FelicaDPTestTheme
import kotlin.getValue

class FelicaActivity : ZebraBaseComponentActivity() {

    private val viewModel by viewModels<FelicaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FelicaDPTestTheme {
                RootView(viewModel)
            }
        }
        viewModel.handleOnCreate(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleOnResume(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.handleOnPause(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        viewModel.handleOnNewIntent(this, intent)
    }

    @Composable
    fun RootView(viewModel: FelicaViewModel) {
        Column(modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
        ) {
            Text(text = "Coding Polarity: ${viewModel.codingPolarityText.value}", color = Color.White)
            Text(text = "Command Type: ${viewModel.commandTypeText.value}", color = Color.White)
            Text(text = "Command: ${viewModel.commandText.value}", color = Color.White)
            Text(text = "----------", color = Color.White)
            Text(text = "Manufacturer: ${viewModel.manufacturerText.value}", color = Color.White)
            Text(text = "Response: ${viewModel.responseText.value}", color = Color.White)
            Row(modifier = Modifier.fillMaxWidth()) {
                CodingButton("Keep") {
                    viewModel.setCodingPolarity(FelicaService.CodingPolarity.Keep)
                }
                CodingButton("+ Coding") {
                    viewModel.setCodingPolarity(FelicaService.CodingPolarity.Positive)
                }
                CodingButton("- Coding") {
                    viewModel.setCodingPolarity(FelicaService.CodingPolarity.Negative)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CommandButton("Cmd 1") {
                    viewModel.setCommandType(FelicaService.CommandType.Command1)
                }
                CommandButton("Cmd 3") {
                    viewModel.setCommandType(FelicaService.CommandType.Command3)
                }
                CommandButton("Cmd 5") {
                    viewModel.setCommandType(FelicaService.CommandType.Command5)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CommandButton("Cmd 7") {
                    viewModel.setCommandType(FelicaService.CommandType.Command7)
                }
                CommandButton("Cmd 9") {
                    viewModel.setCommandType(FelicaService.CommandType.Command9)
                }
                CommandButton("Cmd 11") {
                    viewModel.setCommandType(FelicaService.CommandType.Command11)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                CommandButton("Polling") {
                    viewModel.setCommandType(FelicaService.CommandType.Polling)
                }
                CommandButton("Reset") {
                    viewModel.reset()
                }
            }
        }
    }

    @Composable
    fun CodingButton(title: String, onClick: () -> Unit) {
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = {
                onClick()
            }
            , colors = ButtonColors(
                containerColor = Color(0xFF66BB6A),
                contentColor = Color(0xFFFFFFFF),
                disabledContentColor = Color.White,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text(title)
        }
    }

    @Composable
    fun CommandButton(title: String, onClick: () -> Unit) {
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = {
                onClick()
            }
            , colors = ButtonColors(
                containerColor = Color(0xFF0073E6),
                contentColor = Color(0xFFFFFFFF),
                disabledContentColor = Color.White,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text(title)
        }
    }
}