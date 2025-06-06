package com.example.notesapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.R
import com.example.notesapp.ViewModel
import com.example.notesapp.data.Screen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    navController: NavController,
    vm: ViewModel
) {
    val isChecked by vm.notificationsEnabled.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202326))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.lexend))
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(

            ) {
                Text(
                    text = "User",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
                Text(
                    text = "user@email.com",
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend))
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            NavigationButton(
                name = "Notes",
                onNavigate = {
                    navController.navigate(Screen.NoteScreen.route)
                }
            )
            NavigationButton(
                name = "To-do-lists",
                onNavigate = {
                    navController.navigate(Screen.TodoListScreen.route)
                }
            )
            NavigationButton(
                name = "Trashed files",
                onNavigate = {}
            )
            NavigationButton(
                name = "Notifications",
                onNavigate = {
                    navController.navigate(Screen.NotificationScreen.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Account Settings",
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.lexend))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            NavigationButton(
                name = "EditProfile",
                onNavigate = { }
            )
            NavigationButton(
                name = "Change Password",
                onNavigate = { }
            )
            SimpleSwitch(
                isChecked = isChecked,
                onCheckedChange = {
                    vm.setNotificationsEnabled(it)
                }
            )
            Text(
                text = "Delete Account",
                color = Color(0xFFCC6363),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.clickable(
                    onClick = { },
                    indication = ripple(bounded = false),
                    interactionSource = null
                ),
                fontFamily = FontFamily(Font(R.font.lexend))
            )
            Text(
                text = "Logout",
                color = Color(0xFF2C5BD9),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.clickable(
                    onClick = { },
                    indication = ripple(bounded = false),
                    interactionSource = null
                ),
                fontFamily = FontFamily(Font(R.font.lexend))
            )
        }
    }
}

@Composable
fun NavigationButton(name: String, onNavigate: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onNavigate,
                indication = null,
                interactionSource = null
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.lexend))
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}

@Composable
fun SimpleSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Allow App Notifications",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lexend))

            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = androidx.compose.material3.SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF2C5BD9),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }

    }
}
