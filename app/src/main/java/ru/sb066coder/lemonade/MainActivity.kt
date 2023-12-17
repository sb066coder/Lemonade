package ru.sb066coder.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sb066coder.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    var state by remember {
        mutableStateOf(State(0, State.titles[0], 0))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = state.imageResource),
            modifier = Modifier
                .padding(16.dp)
                .clickable { state = state.change() }
                .background(
                    color = Color(0xFFC5F5D7),
                    shape = RoundedCornerShape(16.dp)
                )
                .height(240.dp)
                .aspectRatio(1.0f, ),
            contentDescription = "Lemon tree"
        )
        Text(text = state.title)
    }
}


class State(
    private val frame: Int,
    val title: String,
    private var extraTaps: Int
    ) {

    val imageResource = when (frame) {
        0 -> R.drawable.lemon_tree
        1 -> R.drawable.lemon_squeeze
        2 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    fun change(): State {
        if (frame == 1 && extraTaps > 0)
            return this.apply { --extraTaps }
        val nextFrame = (frame + 1) % 4
        return State(
            frame = nextFrame,
            title = titles[nextFrame],
            extraTaps = if (nextFrame == 1) (1..3).random() else 0
        )
    }

    companion object {
        val titles = listOf(
            "Tap the lemon tree to select a lemon",
            "Keep tapping the lemon to squeeze it",
            "Tap the lemonade to drink it",
            "Tap the empty glass to start again"
        )
    }
}