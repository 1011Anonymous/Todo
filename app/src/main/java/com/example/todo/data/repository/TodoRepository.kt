package com.example.todo.data.repository

import com.example.todo.data.local.TodoDao
import com.example.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val dao: TodoDao
) {
    suspend fun insertTodo(todo: Todo) : Unit = dao.insert(todo)

    suspend fun updateTodo(todo: Todo) : Unit = dao.update(todo)

    suspend fun deleteTodo(todo: Todo) : Unit = dao.delete(todo)

    suspend fun getTodoById(id: Int) : Todo = dao.getTodoById(id)

    fun getAllTodos() : Flow<List<Todo>> = dao.getAllTodos()
}