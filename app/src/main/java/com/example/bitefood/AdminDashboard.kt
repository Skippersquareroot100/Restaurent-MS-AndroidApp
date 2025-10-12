package com.example.bitefood

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitefood.model.Food
import com.example.bitefood.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminDashboard : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = AppDatabase.getDatabase(this)

        // Insert default foods if not exist
        CoroutineScope(Dispatchers.IO).launch {
            val defaultFoods = listOf(
                Food("Soup", 0.0, 0),
                Food("Burger", 0.0, 0),
                Food("Pizza", 0.0, 0),
                Food("Bento", 0.0, 0),
                Food("Cappuccino", 0.0, 0),
                Food("Fried Chicken", 0.0, 0)
            )
            defaultFoods.forEach { db.foodDao().insertFood(it) }

            // Load initial values to UI
            withContext(Dispatchers.Main) { loadFoodValues() }
        }

        // Setup update buttons
        setupButton(R.id.btnSoupAdd, "Soup", R.id.etSoupPrice, R.id.etSoupQty)
        setupButton(R.id.btnBurgerAdd, "Burger", R.id.etBurgerPrice, R.id.etBurgerQty)
        setupButton(R.id.btnPizzaAdd, "Pizza", R.id.etPizzaPrice, R.id.etPizzaQty)
        setupButton(R.id.btnBentoAdd, "Bento", R.id.etBentoPrice, R.id.etBentoQty)
        setupButton(R.id.btnCappuccinoAdd, "Cappuccino", R.id.etCappuccinoPrice, R.id.etCappuccinoQty)
        setupButton(R.id.btnFriedChickenAdd, "Fried Chicken", R.id.etFriedChickenPrice, R.id.etFriedChickenQty)
    }

    private fun setupButton(buttonId: Int, foodName: String, priceId: Int, qtyId: Int) {
        val btn = findViewById<Button>(buttonId)
        val etPrice = findViewById<EditText>(priceId)
        val etQty = findViewById<EditText>(qtyId)

        btn.setOnClickListener {
            val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val qty = etQty.text.toString().toIntOrNull() ?: 0

            if (price == 0.0 && qty == 0) {
                Toast.makeText(this, "Enter valid price and quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                db.foodDao().updateFood(foodName, price, qty)

                // Fetch updated values from DB to display
                val updatedFood = db.foodDao().getFoodByName(foodName)

                withContext(Dispatchers.Main) {
                    // Update EditTexts with latest DB values
                    etPrice.setText(updatedFood?.price.toString())
                    etQty.setText(updatedFood?.quantity.toString())

                    Toast.makeText(
                        this@AdminDashboard,
                        "$foodName updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun loadFoodValues() {
        mainScope.launch {
            val dao = db.foodDao()
            val foods = listOf(
                Triple("Soup", R.id.etSoupPrice, R.id.etSoupQty),
                Triple("Burger", R.id.etBurgerPrice, R.id.etBurgerQty),
                Triple("Pizza", R.id.etPizzaPrice, R.id.etPizzaQty),
                Triple("Bento", R.id.etBentoPrice, R.id.etBentoQty),
                Triple("Cappuccino", R.id.etCappuccinoPrice, R.id.etCappuccinoQty),
                Triple("Fried Chicken", R.id.etFriedChickenPrice, R.id.etFriedChickenQty)
            )

            foods.forEach { (name, priceId, qtyId) ->
                val food = withContext(Dispatchers.IO) { dao.getFoodByName(name) }
                findViewById<EditText>(priceId).setText(food?.price.toString())
                findViewById<EditText>(qtyId).setText(food?.quantity.toString())
            }
        }
    }
}
