package com.example.bitefood.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bitefood.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: User)

    // Check if username exists
    @Query("SELECT * FROM users WHERE userName = :userName LIMIT 1")
    suspend fun getByUsername(userName: String): User?

    // Login by username and password
    @Query("SELECT * FROM users WHERE userName = :userName AND passWord = :passWord LIMIT 1")
    suspend fun login(userName: String, passWord: String): User?
}
