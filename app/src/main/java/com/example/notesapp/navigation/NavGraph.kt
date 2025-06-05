import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.ViewModel
import com.example.notesapp.data.DataClass
import com.example.notesapp.data.Screen
import com.example.notesapp.screens.HomeScreen
import com.example.notesapp.screens.NewNoteScreen
import com.example.notesapp.screens.NewTodoListScreen
import com.example.notesapp.screens.NoteScreen
import com.example.notesapp.screens.NotificationScreen
import com.example.notesapp.screens.ProfileScreen
import com.example.notesapp.screens.TaskInputCard
import com.example.notesapp.screens.TodoListScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavGraph(vm: ViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                item = DataClass(
                    name = "Meirzhan",
                    all = 10,
                    completed = 5,
                    progress = 50,
                    tasks = 2,
                    notes = 1
                ),
                vm = vm,
                navController = navController
            )
        }
        composable(route = Screen.NoteScreen.route) {
            NoteScreen(navController = navController)
        }
        composable(
            "new_note?id={noteId}", arguments = listOf(
            navArgument("noteId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")?.takeIf { it != -1 }
            NewNoteScreen(navController = navController, vm = viewModel(), noteId = noteId)
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(
                navController
            )
        }
        composable(
            route = Screen.NotificationScreen.route
        ) {
            NotificationScreen(navController)
        }
        composable(
            route = Screen.TodoListScreen.route
        ) {
            TodoListScreen(navController, vm)
        }
        composable(
            route = Screen.NewTodoListScreen.route
        ) {
            NewTodoListScreen()
        }
        composable(
            route = Screen.TaskInputCard.route
        ) {
            TaskInputCard(
                onDone = {
                    navController.popBackStack()
                },
                text = vm.todolistTitle.value,
                onTextChange = {
                    vm.todolistTitle.value = it
                },
                vm
            )
        }
    }
}
