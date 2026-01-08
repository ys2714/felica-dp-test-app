package com.zebra.felicadptest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.zebra.felicadptest.ui.theme.FelicaDPTestTheme

class MainActivity : ZebraBaseComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FelicaDPTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootView(this)
                }
            }
        }
    }

    @Composable
    fun RootView(context: Context) {
        Column(modifier = Modifier
            .padding(vertical = 50.dp)
        ) {
            Text(
                text = "version: ${this@MainActivity.getAppVersion()}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            MenuButton("Felica DP Test") {
                val intent = Intent(context, FelicaActivity::class.java)
                startActivity(context, intent, null)
            }
        }
    }
}

@Composable
fun MenuButton(title: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
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