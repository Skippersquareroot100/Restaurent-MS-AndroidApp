package com.example.bitefood.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bitefood.dao.FoodDao
import com.example.bitefood.model.User
import com.example.bitefood.dao.UserDao
import com.example.bitefood.model.Food


@Database(entities = [User::class, Food::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bitefood_db"
                )   .fallbackToDestructiveMigration()
                    .build()



                INSTANCE = instance
                instance
            }
        }
    }
}
