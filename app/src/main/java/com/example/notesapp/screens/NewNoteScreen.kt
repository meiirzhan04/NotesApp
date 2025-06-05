package com.example.notesapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.ViewModel


@Composable
fun NewNoteScreen(
    navController: NavController,
    vm: ViewModel,
    noteId: Int? = null,
) {
    val title by vm.title.collectAsState()
    val content by vm.content.collectAsState()
    var isClicked by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        if (noteId != null) {
            vm.loadNote(noteId)
        }
    }

    Scaffold(
        containerColor = Color(0xFF202326),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding()
        ) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        vm.title.value = title
                        vm.content.value = content
                        vm.saveNote()
                        navController.popBackStack()
                    }
                )
                Spacer(Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        isClicked = !isClicked
                    }
                )
            }
            if (isClicked) {
                Box(
                    modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopEnd).padding(end = 24.dp),
                ) {
                    DropdownMenu(
                        expanded = isClicked,
                        onDismissRequest = { isClicked = false },
                        containerColor = Color(0xFF_48484A)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete", color = Color.White) },
                            onClick = {
                                isClicked = false
                                vm.deleteNote()
                                navController.popBackStack()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Edit", color = Color.White) },
                            onClick = {
                                isClicked = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Share", color = Color.White) },
                            onClick = {
                                isClicked = false
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
            OutlinedTextField(
                value = title,
                onValueChange = {
                    vm.title.value = it
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                placeholder = { Text(text = "Add Title", color = Color.Gray, fontSize = 32.sp) },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.White
                ),
            )
            OutlinedTextField(
                value = content,
                onValueChange = {
                    vm.content.value = it
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                placeholder = { Text(text = "Add content", color = Color.Gray, fontSize = 18.sp) },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.White
                ),
            )
        }
    }
}
