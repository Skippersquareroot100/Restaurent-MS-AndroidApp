package com.example.bitefood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitefood.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillpageActivity : AppCompatActivity() {
    lateinit var foodName: TextView
    lateinit var foodprice: TextView
    lateinit var FoodQuantity: TextView
    lateinit var Totalamount: TextView
    lateinit var minus: Button
    lateinit var plus: Button
    lateinit var Confirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_billpage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        foodName = findViewById(R.id.foodname)
        foodprice = findViewById(R.id.foodprice)
        FoodQuantity = findViewById(R.id.foodquantity)
        Totalamount = findViewById(R.id.totalamount)
        minus = findViewById(R.id.minusbtn)
        plus = findViewById(R.id.plusbtn)
        Confirm = findViewById(R.id.totalbill)

        val item = intent.getStringExtra("item")
        foodName.text = item ?: ""
        var unitquantity = 1
        FoodQuantity.text = unitquantity.toString()

        val db = AppDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            val food = db.foodDao().getFoodByName(item ?: "")
            if (food != null) {
                val price = food.price
                runOnUiThread {
                    foodprice.text = price.toString()
                    Totalamount.text = (price * unitquantity).toString()

                    plus.setOnClickListener {
                        unitquantity++
                        FoodQuantity.text = unitquantity.toString()
                        Totalamount.text = (price * unitquantity).toString()
                    }

                    minus.setOnClickListener {
                        if (unitquantity > 1) {
                            unitquantity--
                            FoodQuantity.text = unitquantity.toString()
                            Totalamount.text = (price * unitquantity).toString()
                        }
                    }

                    Confirm.setOnClickListener {
                        Toast.makeText(
                            this@BillpageActivity,
                            "Order placed successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@BillpageActivity, HomepageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}
