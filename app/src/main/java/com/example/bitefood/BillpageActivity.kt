package com.example.bitefood

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BillpageActivity : AppCompatActivity() {
    lateinit var foodName:TextView
    lateinit var foodprice:TextView
    lateinit var FoodQuantity:TextView
    lateinit var Totalamount:TextView
    lateinit var minus:Button
    lateinit var plus:Button
    lateinit var Confirm:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_billpage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val item = intent.getStringExtra("item")
        val price = intent.getIntExtra("price", 0)

        foodName=findViewById(R.id.foodname)
        foodName.setText(item ?: "")
        foodprice=findViewById(R.id.foodprice)
        FoodQuantity=findViewById(R.id.foodquantity)
        Totalamount=findViewById(R.id.totalamount)
        minus=findViewById(R.id.minusbtn)
        plus=findViewById(R.id.plusbtn)
        Confirm=findViewById(R.id.totalbill)

        foodprice.setText(price.toString())
        var unitprice = price
        var unitquantity = 1
        Totalamount.text = (unitprice * unitquantity).toString()
        FoodQuantity.text = unitquantity.toString()

        fun updatebillplus(){
            unitquantity++
            FoodQuantity.text = unitquantity.toString()
            Totalamount.text = (unitprice * unitquantity).toString()
        }
        fun updatebillminus(){
            if (unitquantity > 1){
                unitquantity--
                FoodQuantity.text = unitquantity.toString()
                Totalamount.text = (unitprice * unitquantity).toString()
            }
        }
        plus.setOnClickListener {
            updatebillplus()
        }
        minus.setOnClickListener {
            updatebillminus()
        }
    }
}

