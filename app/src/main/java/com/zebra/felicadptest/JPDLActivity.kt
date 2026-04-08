package com.zebra.felicadptest

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zebra.felicadptest.ui.theme.FelicaDPTestTheme

class JPDLActivity : ZebraBaseComponentActivity() {

    val viewModel = JPDLViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FelicaDPTestTheme {
                RootView(this)
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.handleOnDestroy(this)
    }

    @Composable
    fun RootView(context: Context) {
        Column(modifier = Modifier
            .padding(vertical = 50.dp)
        ) {
            Text(
                text = "Please Tap Driver License Test Card",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "(default PIN 1234)",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "DO NOT TAP YOUR OWN DRIVER LICENSE!!!",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "-------------------------------------",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "version:\n${viewModel.version.value}",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "issueDate:\n${viewModel.issueDate.value}",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "expireDate:\n${viewModel.expireDate.value}",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "manufacturerID:\n${viewModel.manufacturerID.value}",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "encryptionID:\n${viewModel.encryptionID.value}",
                color = Color.White,
                modifier = Modifier.align(Alignment.Start)
            )
            MenuButton("Reset") {
                viewModel.reset()
            }
        }
    }
}