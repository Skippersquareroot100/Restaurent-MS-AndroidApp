package com.example.bitefood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginPageActivity : AppCompatActivity() {
    lateinit var username:EditText
    lateinit var password:EditText
     lateinit var LogIn:Button
     lateinit var regesterlink:TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         username=findViewById(R.id.namefield)
        password=findViewById(R.id.passwordfield)
        LogIn=findViewById(R.id.btn1)
        regesterlink=findViewById(R.id.Reglink)
        LogIn.setOnClickListener {
            if(username.text.toString().isEmpty()|| password.text.toString().isEmpty())
            {
                Toast.makeText(this, "Fill The UserName And Password First", Toast.LENGTH_SHORT).show()
            }


            if(username.text.toString()=="samia12" && password.text.toString()=="123@")
            {
                startActivity(Intent(this,HomepageActivity::class.java))
            }
            else{
                Toast.makeText(this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show()
            }

        }
        regesterlink.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }




    }
}