package com.example.bitefood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomepageActivity : AppCompatActivity() {
    lateinit var soup: Button
    lateinit var Burger: Button
    lateinit var Pizza: Button
    lateinit var FoodBento: Button
    lateinit var Cappuccino: Button
    lateinit var Chikenfry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        soup = findViewById(R.id.soupbutton)
        Burger = findViewById(R.id.burgerbutton)
        Pizza = findViewById(R.id.pizzabutton)
        FoodBento = findViewById(R.id.foodbentobutton)
        Cappuccino = findViewById(R.id.coffeebutton)
        Chikenfry = findViewById(R.id.chikenbutton)

        soup.setOnClickListener {
            val intent = Intent(this, BillpageActivity::class.java)
            intent.putExtra("item", "Soup")
            intent.putExtra("price", 200)
            startActivity(intent)
        }
        Burger.setOnClickListener {
            val intent = Intent(this, BillpageActivity::class.java)
            intent.putExtra("item", "Burger")
            intent.putExtra("price", 300)
            startActivity(intent)
        }
        Pizza.setOnClickListener {
            val intent = Intent(this, BillpageActivity::class.java)
            intent.putExtra("item", "Pizza")
            intent.putExtra("price", 600)
            startActivity(intent)
        }
        FoodBento.setOnClickListener {
            val intent = Intent(this, BillpageActivity::class.java)
            intent.putExtra("item", "Food Bento")
            intent.putExtra("price", 400)
            startActivity(intent)
        }
        Cappuccino.setOnClickListener {
            val intent = Intent(this, BillpageActivity::class.java)
            intent.putExtra("item", "Cappuccino")
            intent.putExtra("price", 280)
            startActivity(intent)
        }
        Chikenfry.setOnClickListener {
            val intent = Intent(this, BillpageActivity::class.java)
            intent.putExtra("item", "Chiken Fry")
            intent.putExtra("price", 250)
            startActivity(intent)
        }
    }
}
