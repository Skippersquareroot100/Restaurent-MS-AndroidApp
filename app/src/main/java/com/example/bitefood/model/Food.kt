package com.example.bitefood.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "foods")
data class  Food (
    @PrimaryKey(autoGenerate = false)
    val name: String,
    var price: Double,
    var quantity: Int
)

