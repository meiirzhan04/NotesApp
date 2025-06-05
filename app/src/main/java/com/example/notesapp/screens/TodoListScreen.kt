package com.example.notesapp.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.R
import com.example.notesapp.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navController: NavController,
    vm: ViewModel
) {
    var showTaskInput by remember { mutableStateOf(false) }
    val selectedTasks = vm.isClicked.filter { it.value }.keys.toList()

    val resetSheetState = rememberModalBottomSheetState()
    if (showTaskInput) {
        ModalBottomSheet(
            onDismissRequest = {
                showTaskInput = false
            },
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            containerColor = Color(0xFF202326),
            sheetState = resetSheetState,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            dragHandle = {},
            content = {
                TaskInputCard(
                    onDone = { task ->
                        showTaskInput = false
                    },
                    text = vm.editingTaskTitle.value,
                    onTextChange = {
                        vm.editingTaskTitle.value = it
                    },
                    vm
                )
            }
        )
    }
    Scaffold(
        containerColor = Color(0xFF202326),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.editingTaskId.value = null
                    vm.editingTaskTitle.value = ""
                    showTaskInput = true
                },
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
                .background(Color(0xFF_202326))
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Header(
                navController,
                onClick = { navController.popBackStack() },
                show = true,
                onDelete = {
                    vm.deleteTasks(selectedTasks)
                    selectedTasks.forEach { id -> vm.isClicked.remove(id) }
                },
                enabled = selectedTasks.isNotEmpty()
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = "To-do-list",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.lexend)),
                letterSpacing = 0.5.sp
            )
            Spacer(Modifier.height(32.dp))
            TaskTabsWithContent(
                vm = vm,
                onClick = { showTaskInput = true }
            )
            Spacer(Modifier.height(32.dp))

        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TaskCard(
    task: String,
    taskId: Int,
    vm: ViewModel,
    onClick: () -> Unit = {},
    taskDeadline: Long?
) {
    val isChecked = vm.isClicked.getOrElse(taskId) { false }
    val formattedDeadline = taskDeadline?.let {
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(it))
    } ?: "Без дедлайна"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = ripple(bounded = true),
                interactionSource = null
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF48484A)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(
                    text = task,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                    modifier = Modifier.fillMaxWidth(0.9f),
                )
                Spacer(Modifier.weight(1f))
                CircularCheckbox(
                    onCheckedChange = { vm.isClicked[taskId] = it },
                    isClicked = isChecked
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Deadline: $formattedDeadline",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CircularCheckbox(
    onCheckedChange: (Boolean) -> Unit,
    isClicked: Boolean
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = if (isClicked) Color.Gray else Color.Transparent,
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = if (isClicked) Color.Gray else Color.Gray,
                shape = CircleShape
            )
            .clickable(
                indication = ripple(bounded = false),
                interactionSource = null
            ) {
                onCheckedChange(!isClicked)
            },
        contentAlignment = Alignment.Center
    ) {
        if (isClicked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TaskTabsWithContent(vm: ViewModel, onClick: () -> Unit) {
    val tabs = listOf("Today's", "Pending", "All Tasks")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val allTasks by vm.todolists.collectAsState(initial = emptyList())

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    val tabPosition = tabPositions[selectedTabIndex]
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPosition)
                            .height(3.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF00C6FF), Color(0xFF0072FF))
                                ),
                                shape = RoundedCornerShape(50)
                            )
                    )
                }
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    selectedContentColor = Color.Transparent,
                    unselectedContentColor = Color.Transparent,
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color(0xFF00C6FF) else Color.White,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> {
                Text(
                    "Пока нет сохранённых заметок", color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
            }

            1 -> {
                Text(
                    "Пока нет сохранённых заметок", color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
            }

            2 -> {
                if (allTasks.isEmpty()) {
                    Text(
                        "Пока нет сохранённых заметок", color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.lexend))
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(allTasks, key = { it.id }) { note ->
                            TaskCard(
                                task = note.title,
                                taskId = note.id,
                                taskDeadline = note.deadline,
                                vm = vm,
                                onClick = {
                                    vm.editingTaskId.value = note.id
                                    vm.editingTaskTitle.value = note.title
                                    vm.deadline.value = note.deadline
                                    onClick()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TaskInputCard(
    onDone: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit = {},
    vm: ViewModel,
) {
    var selectedDeadline by remember { mutableStateOf<Long?>(null) }


    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = {},
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    uncheckedColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                ),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend))
                ),
                cursorBrush = SolidColor(Color(0xFF00C6FF)),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            "Нажмите \"Ввод\" для создания подзадач",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    }
                    innerTextField()

                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            DeadlinePicker(
                currentDeadline = selectedDeadline,
                onDeadlineSelected = { selectedDeadline = it }
            )
            TextButton(
                onClick = {
                    if (text.isBlank()) return@TextButton
                    if (vm.editingTaskId.value != null) {
                        vm.updateTodolist(vm.editingTaskId.value!!, text, selectedDeadline)
                    } else {
                        vm.saveTodolist(text, selectedDeadline)
                    }
                    vm.editingTaskId.value = null
                    vm.editingTaskTitle.value = ""
                    vm.deadline.value = null
                    onDone(text)
                }
            ) {
                Text(
                    text = "Готово",
                    color = if (text.isNotBlank()) Color(0xFF00C6FF) else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun DeadlinePicker(
    currentDeadline: Long?,
    onDeadlineSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)

            val timePickerDialog = TimePickerDialog(
                context,
                { _, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    onDeadlineSelected(calendar.timeInMillis)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Button(
        onClick = { datePickerDialog.show() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Напоминание",
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("Напоминание", color = Color.White, fontSize = 14.sp)
    }

    currentDeadline?.let {
        Text(
            text = "Дедлайн: ${
                SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault()).format(
                    Date(
                        it
                    )
                )
            }",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
