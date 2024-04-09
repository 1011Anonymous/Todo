package com.example.todo.presentation.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.domain.model.Todo
import com.example.todo.presentation.MainViewModel
import com.example.todo.presentation.common.taskStyle
import com.example.todo.presentation.common.toastMsg

@Composable
fun AlertDialog_HomeScreen(
    openDialog: Boolean,
    onClose: () -> Unit,
    mainViewModel: MainViewModel
) {
    var task by remember {
        mutableStateOf("")
    }
    var isImportant by remember {
        mutableStateOf(false)
    }
    val todo = Todo(task = task, isImportant = isImportant)
    val focusRequester = remember {
        FocusRequester()
    }
    val context = LocalContext.current
    //val focusManager = LocalFocusManager.current

    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = "Todo",
                    fontFamily = FontFamily.Serif
                )
            },
            text = {
                LaunchedEffect(key1 = Unit) {
                    focusRequester.requestFocus()
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = task,
                        onValueChange = { task = it },
                        placeholder = {
                            Text(
                                text = "Add Task",
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        shape = RectangleShape,
                        modifier = Modifier.focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (task.isNotBlank()) {
                                    mainViewModel.insertTodo(todo)
                                    task = ""
                                    isImportant = false
                                    onClose()
                                    //focusManager.clearFocus()
                                } else {
                                    toastMsg(
                                        context,
                                        "Empty Task!!!"
                                    )
                                }
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { task = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        textStyle = taskStyle
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Important",
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Checkbox(
                            checked = isImportant,
                            onCheckedChange = { isImportant = it }
                        )
                    }
                }
            },
            onDismissRequest = {
                onClose()
                task = ""
                isImportant = false
            },
            confirmButton = {
                TextButton(onClick = {
                    if (task.isNotBlank()) {
                        mainViewModel.insertTodo(todo)
                        task = ""
                        isImportant = false
                        onClose()
                        //focusManager.clearFocus()
                    } else {
                        toastMsg(
                            context,
                            "Empty Task!!!"
                        )
                    }
                }) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onClose()
                    task = ""
                    isImportant = false
                }) {
                    Text(text = "Close")
                }
            }
        )
    }

}