package ru.sb066coder.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        mutableStateOf(State(0))
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
                    shape = RoundedCornerShape(32.dp)
                )
                .height(240.dp)
                .aspectRatio(1.0f),
            contentDescription = when(state.frame) {
                1 -> stringResource(R.string.lemon_tree)
                2 -> stringResource(R.string.lemon)
                3 -> stringResource(R.string.glass_of_lemonade)
                else -> stringResource(R.string.empty_glass)
            }
        )
        Text(
            text = when(state.frame) {
                1 -> stringResource(id = R.string.tap_the_lemon_tree)
                2 -> stringResource(id = R.string.keep_tapping_the_lemon)
                3 -> stringResource(id = R.string.tap_the_lemonade)
                else -> stringResource(id = R.string.tap_the_empty_glass)
            },
            fontSize = 18.sp
        )
    }
}


class State(
    val frame: Int
    ) {

    val imageResource = when (frame) {
        0 -> R.drawable.lemon_tree
        1 -> R.drawable.lemon_squeeze
        2 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    private var extraTaps = if (frame == 1) (1..3).random() else 0

    fun change(): State {
        if (frame == 1 && extraTaps > 0)
            return this.apply { --extraTaps }
        return State((frame + 1) % 4)
    }
}