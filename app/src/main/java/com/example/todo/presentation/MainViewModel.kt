package com.example.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.repository.TodoRepository
import com.example.todo.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    var todo by mutableStateOf(Todo(task = "", isImportant = false))
        private set
    val getAllTodos = repository.getAllTodos()
    private var deletedTodo: Todo? = null

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
            deletedTodo = todo
        }
    }

    fun undoDeleteTodo(deleteTodo: Todo) {
        deletedTodo?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo(it)
            }
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todo = repository.getTodoById(id)
        }
    }

    fun updateTask(newTask: String) {
        todo = todo.copy(task = newTask)
    }

    fun updateIsImportant(newIsImportant: Boolean) {
        todo = todo.copy(isImportant = newIsImportant)
    }
}