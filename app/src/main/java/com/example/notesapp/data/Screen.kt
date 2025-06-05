package com.example.notesapp.data


sealed class Screen(val route: String) {
    object HomeScreen    : Screen("home")
    object NoteScreen    : Screen("notes")
    object NewNoteScreen : Screen("new_note") {
        fun createRoute(noteId: Int) = "new_note?id=$noteId"
    }
    object ProfileScreen : Screen("profile")
    object NotificationScreen : Screen("notification")
    object TodoListScreen : Screen("todo_list")
    object NewTodoListScreen : Screen("new_todo_list")
    object TaskInputCard : Screen("task_input_card")
}
