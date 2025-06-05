package com.example.notesapp.screens

import android.R.attr.fontFamily
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.R
import com.example.notesapp.ViewModel
import com.example.notesapp.data.DataClass
import com.example.notesapp.data.Screen
import com.example.notesapp.ui.theme.BlueGradient
import kotlin.math.roundToInt

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    item: DataClass,
    vm: ViewModel,
    navController: NavController,
) {
    val notes by vm.notes.collectAsState(initial = emptyList())
    val sumNotes = notes.size
    val todolists by vm.todolists.collectAsState(initial = emptyList())
    val sumTodolists = todolists.size
    val selectedTasks = vm.isClicked.filter { it.value }.keys.toList().size
    val progress =
        if (sumTodolists != 0) (selectedTasks.toDouble() / sumTodolists * 100).roundToInt() else 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF_202326))
            .padding(vertical = 24.dp, horizontal = 24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Hello ${item.name}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Favorite",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Favorite",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(
                            onClick = {
                                navController.navigate(Screen.ProfileScreen.route)
                            }
                        )
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(Color(0xFF_48484A))
                    .padding(18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Today's Progress",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "You have completed $selectedTasks of the $sumTodolists tasks, \nkeep up the progress!",
                            fontSize = 11.sp,
                            color = Color(0x99FFFFFF),
                            letterSpacing = 1.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(shape = CircleShape)
                            .background(BlueGradient)
                    ) {
                        Text(
                            text = "$progress%",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier.align(Alignment.Center),
                            letterSpacing = 1.sp,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(shape = RoundedCornerShape(24.dp))
                        .background(Color(0xFF_48484A))
                        .padding(18.dp)
                        .clickable(
                            onClick = {
                                navController.navigate(Screen.NoteScreen.route)
                            },
                            indication = ripple(bounded = true),
                            interactionSource = null
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.notes),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Notes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            letterSpacing = 1.sp,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                        Text(
                            text = "$sumNotes notes",
                            fontSize = 14.sp,
                            color = Color(0xDDFFFFFF),
                            letterSpacing = 1.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(shape = RoundedCornerShape(24.dp))
                        .background(Color(0xFF_48484A))
                        .padding(18.dp)
                        .clickable(
                            onClick = {
                                navController.navigate(Screen.TodoListScreen.route)
                            },
                            indication = ripple(bounded = true),
                            interactionSource = null
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notes2),
                            contentDescription = "Favorite",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "To-do",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            letterSpacing = 1.sp,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                        Text(
                            text = "$sumTodolists tasks",
                            fontSize = 14.sp,
                            color = Color(0xDDFFFFFF),
                            letterSpacing = 1.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pending Tasks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
                Text(
                    text = "See All",
                    fontSize = 14.sp,
                    color = Color(0xDDFFFFFF),
                    letterSpacing = 1.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable(
                        onClick = {

                        },
                        indication = ripple(bounded = true),
                        interactionSource = null
                    ),
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
            }
        }
    }
}