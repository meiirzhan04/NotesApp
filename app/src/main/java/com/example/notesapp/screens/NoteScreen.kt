package com.example.notesapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notesapp.R
import com.example.notesapp.ViewModel
import com.example.notesapp.data.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteScreen(
    navController: NavController,
    vm: ViewModel = viewModel(),
) {
    val noteList by vm.notes.collectAsState(initial = emptyList())

    Scaffold(
        containerColor = Color(0xFF202326),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.NewNoteScreen.route) },
                containerColor = Color.Blue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add note")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Header(navController, onClick = { navController.popBackStack() }, show = false, enabled = false)
            Spacer(Modifier.height(32.dp))

            Text(
                text = "Notes",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.lexend))
            )
            Spacer(Modifier.height(32.dp))

            if (noteList.isEmpty()) {
                Text(
                    "Пока нет сохранённых заметок", color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(noteList, key = { it.id }) { note ->
                        NoteCard(
                            title = note.title,
                            content = note.content,
                            date = note.date,
                            onNoteClick = {
                                navController.navigate(Screen.NewNoteScreen.createRoute(note.id))
                                vm.title.value = note.title
                                vm.content.value = note.content
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun Header(navController: NavController, onClick: () -> Unit = {}, show: Boolean, onDelete: () -> Unit = {}, enabled: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.clickable(
                onClick = onClick,
                indication = ripple(bounded = true),
                interactionSource = null
            )
        )
        Spacer(Modifier.weight(1f))
        if (show) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier.clickable(
                    onClick = onDelete,
                    enabled = enabled,
                    indication = ripple(bounded = true),
                    interactionSource = null
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = "Notifications",
            tint = Color.White,
            modifier = Modifier.clickable {
                navController.navigate(Screen.NotificationScreen.route)
            }
        )
        Spacer(Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile",
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    navController.navigate(Screen.ProfileScreen.route)
                }
        )
    }
}

@Composable
fun NoteCard(title: String, content: String, date: Long, onNoteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.6f)
            .clickable(
                onClick = { onNoteClick() },
                indication = ripple(bounded = true),
                interactionSource = null
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF48484A))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(Font(R.font.lexend))
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = content,
                color = Color(0xFF_D3D4DD),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(Font(R.font.lexend))
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = formatDate(date),
                color = Color.LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.lexend)),
            )
        }

    }
}

fun formatDate(time: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(time))
}
/*

@Composable
fun AddOptions(
    onCreateNotes: () -> Unit,
    onCreateCategory: () -> Unit
) {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            OptionRow("Create New Notes", onClick = onCreateNotes)
            OptionRow("Create New Notes Category", onClick = onCreateCategory)
        }
    }
}

@Composable
fun OptionRow(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text, color = Color.White, fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.lexend))
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun NewNotesSheet() {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            Text(
                text = "New Notes",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.lexend))
            )
        }
    }
}

@Composable
fun NewCategorySheet(
    title: String,
    onTitleChange: (String) -> Unit,
    onCreate: () -> Unit
) {
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            Text(
                text = "New Notes Category",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.lexend))
            )
            Spacer(Modifier.height(30.dp))
            Text(
                "Category Name",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.lexend))
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Category Name", color = Color.LightGray,
                        fontFamily = FontFamily(Font(R.font.lexend))
                    )
                },
                shape = RoundedCornerShape(12.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                singleLine = true
            )
            Spacer(Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF_01F0FF),
                                Color(0xFF_4441ED)
                            ),
                        )
                    )
                    .clickable(
                        onClick = onCreate,
                        indication = ripple(bounded = true),
                        interactionSource = null
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "Create",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
            }
        }
    }
}

@Composable
fun CustomDragHandle() {
    Box(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .size(width = 35.dp, height = 5.dp)
            .background(Color.White, RoundedCornerShape(2.dp))
    )
}
*/
