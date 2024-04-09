package com.example.todo.data.local

import android.view.TouchDelegate
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM TODO WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo

    @Query("SELECT * FROM Todo")
    fun getAllTodos(): Flow<List<Todo>>
}