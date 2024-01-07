package com.example.tutorial

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryStart(size = 8)
        }
    }
}

@Composable
fun MemoryStart(size:Int){
    BackGround(R.drawable.background)
    val rnd = remember { ((1..size/2).toList() + (1..size/2).toList()).shuffled() }
    MemoryGame(list = rnd)
}

@Composable
fun BackGround(imageRes: Int){
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MemoryGame(list: List<Int>){
    var visible = remember { MutableList(2*list.size) {true} }
    var counter by remember { mutableIntStateOf(1) }
    var prevIndex by remember { mutableIntStateOf(-1) }
    var index by remember { mutableIntStateOf(-1) }
    var change = remember { true }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        Text(text = "counter: $counter  index: $index  prev: $prevIndex")
    }

    LaunchedEffect(counter){
        if(counter % 3 == 0){
            delay(2000)
            if(list[index] == list[prevIndex]) { //eltüntessük a kártyákat
                change = true
                visible[index] = false
                visible[prevIndex] = false
            }
            else { //visszaforgatunk
                change = false
                visible[index + list.size] = true
                visible[prevIndex + list.size] = true
            }
            counter++
        }
    }


    for(i in 0..1){ //először a képek
        //if(!change && i == 0) continue //ha változás van, csak akkor kell újragenerálni a hátlapokat
        
        for(col in 0..1){ //2 oszlopban
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = if(col == 0) Alignment.CenterStart else Alignment.CenterEnd
            ){
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    for(row in 0..3){ //4 sorban
                        val currentIndex = col*4+row
                        Row (modifier = Modifier.size(100.dp))
                        {
                            if(visible[currentIndex + i*list.size]){
                                Image(
                                    painter = if (i == 1) painterResource(id = R.drawable.back) else
                                        when (list[currentIndex]) {
                                            1 -> painterResource(id = R.drawable.img1)
                                            2 -> painterResource(id = R.drawable.img2)
                                            3 -> painterResource(id = R.drawable.img3)
                                            4 -> painterResource(id = R.drawable.img4)
                                            else -> painterResource(id = R.drawable.unknown)
                                        },
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        if(currentIndex != prevIndex) { //ha nem ugyanazt a kártyát nyomkodja
                                            visible[currentIndex + i*list.size] = false
                                            if (counter % 3 == 2) { //ha a 2. kártyát forgatja
                                                index = currentIndex
                                            } else {
                                                prevIndex = currentIndex
                                            }
                                            counter++
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HangMan(word: String)
{
    BackGround(imageRes = R.drawable.background)
    val letters = ('A'..'Z').toList()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ){
        for(line in 0..4){
            Row(modifier = Modifier.height(12.dp).padding(25.dp)){
                Text(text = "helo")
            }
        }
    }
}



@Preview
@Composable
fun MyPreview(){
    HangMan(word = "Hangman")
}