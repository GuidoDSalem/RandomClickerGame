package com.practice.randomclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.practice.randomclicker.ui.theme.RandomClickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomClickerTheme {

            }
        }
    }
}

@Composable
fun MainScreen() {
    var points by remember{
        mutableStateOf(0)
    }
    var isStarted by remember{
        mutableStateOf(false)
    }
    var seconds by remember{mutableStateOf(0L)}
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Points $points",
                fontSize = 20.sp,
                style = TextStyle()

            )
            Button(onClick = { /*TODO*/ }) {
                Text(text = if(isStarted)"Restart" else "Start")
            }
            Text(text = "Time: ${seconds.toString()}"+"s")
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),

        ) {

        }


    }

}

