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

class RegistrationActivity : AppCompatActivity() {

    lateinit var email:EditText
    lateinit var userName:EditText
    lateinit var PassWord:EditText
    lateinit var confirmpassword:EditText
    lateinit var createAccount:Button
    lateinit var acchave:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        email=findViewById(R.id.emailtext)
        userName=findViewById(R.id.usernametext)
        PassWord=findViewById(R.id.passtext)
        confirmpassword=findViewById(R.id.confirmpasstext)
        createAccount=findViewById(R.id.signupbtn)
        acchave=findViewById(R.id.alreadytext)
        createAccount.setOnClickListener {
           if( email.text.toString().isEmpty()|| userName.text.toString().isEmpty()|| PassWord.text.toString().isEmpty()||confirmpassword.text.toString().isEmpty())
            {
                Toast.makeText(this, "Fill The All Field First", Toast.LENGTH_SHORT).show()
            }
            else{
               Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
               startActivity(Intent(this,LoginPageActivity::class.java))

            }

        }
        acchave.setOnClickListener {  startActivity(Intent(this,LoginPageActivity::class.java)) }
    }
}