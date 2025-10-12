package com.example.bitefood.dao


import androidx.room.*
import com.example.bitefood.model.Food

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods")
    suspend fun getAllFoods(): List<Food>

    @Query("SELECT * FROM foods WHERE name = :foodName LIMIT 1")
    suspend fun getFoodByName(foodName: String): Food?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food)



    @Query("UPDATE foods SET price = :price, quantity = :quantity WHERE name = :foodName")
    suspend fun updateFood(foodName: String, price: Double, quantity: Int)
}