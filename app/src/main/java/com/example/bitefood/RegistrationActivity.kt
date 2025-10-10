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
import androidx.lifecycle.lifecycleScope
import com.example.bitefood.model.User
import com.example.bitefood.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var userName: EditText
    lateinit var PassWord: EditText
    lateinit var confirmpassword: EditText
    lateinit var createAccount: Button
    lateinit var acchave: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = findViewById(R.id.emailtext)
        userName = findViewById(R.id.usernametext)
        PassWord = findViewById(R.id.passtext)
        confirmpassword = findViewById(R.id.confirmpasstext)
        createAccount = findViewById(R.id.signupbtn)
        acchave = findViewById(R.id.alreadytext)

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        createAccount.setOnClickListener {
            val emailText = email.text.toString().trim()
            val userNameText = userName.text.toString().trim()
            val passText = PassWord.text.toString().trim()
            val confirmPassText = confirmpassword.text.toString().trim()

            if (emailText.isEmpty() || userNameText.isEmpty() || passText.isEmpty() || confirmPassText.isEmpty()) {
                Toast.makeText(this, "Fill All Fields", Toast.LENGTH_SHORT).show()
            } else if (passText != confirmPassText) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(
                    email = emailText,
                    userName = userNameText,
                    passWord = passText
                )

                // ✅ Use coroutine to call suspend function
                lifecycleScope.launch(Dispatchers.IO) {
                    userDao.registerUser(user)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegistrationActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegistrationActivity, LoginPageActivity::class.java))
                        finish()
                    }
                }
            }
        }

        acchave.setOnClickListener {
            startActivity(Intent(this, LoginPageActivity::class.java))
        }
    }
}
