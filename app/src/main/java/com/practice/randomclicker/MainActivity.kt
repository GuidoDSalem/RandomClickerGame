package com.practice.randomclicker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.randomclicker.ui.theme.RandomClickerTheme
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomClickerTheme {
                MainScreen()
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Points $points",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold

            )
            Button(onClick = {
                isStarted = !isStarted
                points = 0
            }) {
                Text(text = if(isStarted)"Reset" else "Start")
            }
            CountDownTimer(isTimeRunning = isStarted){
                isStarted = false

            }
        }
        BallClicker(
            radius = 100f,
            enabled = isStarted,
        ){
            points++
        }
    }

}

@Composable
fun CountDownTimer(
    time: Int = 30*1000,
    isTimeRunning: Boolean = false,
    onTimerEnd: ()->Unit = {}
) {
    var currentTime by remember{
        mutableStateOf(time)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimeRunning){
        if(!isTimeRunning){
            currentTime = time
            return@LaunchedEffect
        }
        if(currentTime > 0L){
            delay(1*1000L)
            currentTime -= 1*1000
        }
        else{
            onTimerEnd()
        }
    }
    Text(text = "Time left: ${(currentTime/1000L).toInt()} s")

}

@Composable
fun BallClicker(
    radius: Float,
    enabled: Boolean,
    ballColor: Color = Color.Red,
    onBallClick: ()->Unit = {}
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        var ballPosition by remember{
            mutableStateOf(randomPosition(radius,constraints.maxHeight,constraints.maxWidth))
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(enabled){
                    if(!enabled){
                        return@pointerInput
                    }
                    detectTapGestures {
                        val tapPosition = Offset(x=it.x,y=it.y)
                        val distanceBallTap = distanceM2(tapPosition,ballPosition)
                        if (distanceBallTap <= radius){
                            ballPosition = randomPosition(radius,constraints.maxHeight,constraints.maxWidth)
                            onBallClick()
                        }
                    }
                }
            ,

        ){
            drawCircle(
                color = ballColor,
                radius = radius,
                center = ballPosition
            )
        }

    }

    
}

private fun randomPosition(
    radius: Float,
    height:Int,
    width: Int,
): Offset {

    val x = Random.nextInt(radius.roundToInt(),width - radius.roundToInt())
    val y = Random.nextInt(radius.roundToInt(),height - radius.roundToInt())
    return Offset(x.toFloat(),y.toFloat())
}

private fun distanceM2(
    p1:Offset,
    p2:Offset
):Float{
    return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))
}
