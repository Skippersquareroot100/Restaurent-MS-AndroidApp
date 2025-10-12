package com.example.bitefood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitefood.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepageActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    lateinit var soup: Button
    lateinit var burger: Button
    lateinit var pizza: Button
    lateinit var foodBento: Button
    lateinit var cappuccino: Button
    lateinit var chickenFry: Button

    lateinit var soupTitle: TextView
    lateinit var burgerTitle: TextView
    lateinit var pizzaTitle: TextView
    lateinit var bentoTitle: TextView
    lateinit var coffeeTitle: TextView
    lateinit var chickenTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = AppDatabase.getDatabase(this)

        soup = findViewById(R.id.soupbutton)
        burger = findViewById(R.id.burgerbutton)
        pizza = findViewById(R.id.pizzabutton)
        foodBento = findViewById(R.id.foodbentobutton)
        cappuccino = findViewById(R.id.coffeebutton)
        chickenFry = findViewById(R.id.chikenbutton)

        soupTitle = findViewById(R.id.souptitle)
        burgerTitle = findViewById(R.id.burgertitle)
        pizzaTitle = findViewById(R.id.pizzatitle)
        bentoTitle = findViewById(R.id.foodbentotitle)
        coffeeTitle = findViewById(R.id.coffeetitle)
        chickenTitle = findViewById(R.id.chikentitle)

        // Load quantities from DB
        loadQuantities()

        // Order button clicks
        setupButton(soup, "Soup", 200)
        setupButton(burger, "Burger", 300)
        setupButton(pizza, "Pizza", 600)
        setupButton(foodBento, "Bento", 400)
        setupButton(cappuccino, "Cappuccino", 280)
        setupButton(chickenFry, "Fried Chicken", 250)
    }

    private fun setupButton(button: Button, foodName: String, price: Int) {
        button.setOnClickListener {
            val intent = Intent(this, BillpageActivity::class.java)
            intent.putExtra("item", foodName)
            intent.putExtra("price", price)
            startActivity(intent)
        }
    }

    private fun loadQuantities() {
        CoroutineScope(Dispatchers.IO).launch {
            val foods = db.foodDao().getAllFoods()

            withContext(Dispatchers.Main) {
                foods.forEach { food ->
                    when (food.name) {
                        "Soup" -> soupTitle.text = "Soup (Qty: ${food.quantity})"
                        "Burger" -> burgerTitle.text = "Burger (Qty: ${food.quantity})"
                        "Pizza" -> pizzaTitle.text = "Pizza (Qty: ${food.quantity})"
                        "Bento" -> bentoTitle.text = "Food Bento (Qty: ${food.quantity})"
                        "Cappuccino" -> coffeeTitle.text = "Cappuccino (Qty: ${food.quantity})"
                        "Fried Chicken" -> chickenTitle.text = "Fried Chicken (Qty: ${food.quantity})"
                    }
                }
            }
        }
    }
}
