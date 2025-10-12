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
import com.example.bitefood.data.AppDatabase
import com.example.bitefood.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPageActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var LogIn: Button
    lateinit var regesterlink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        username = findViewById(R.id.namefield)
        password = findViewById(R.id.passwordfield)
        LogIn = findViewById(R.id.btn1)
        regesterlink = findViewById(R.id.Reglink)

        // ===== Location Permission Check =====
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            }
        }

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        LogIn.setOnClickListener {
            val usernameText = username.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Fill The UserName And Password First", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Admin login
            if (usernameText == "samia123" && passwordText == "123") {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AdminDashboard::class.java))
                finish()
                return@setOnClickListener
            }

            // User login from DB
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val user: User? = userDao.login(usernameText, passwordText)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            Toast.makeText(this@LoginPageActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginPageActivity, HomepageActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginPageActivity, "Invalid Username Or Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginPageActivity, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        regesterlink.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    // ===== Handle Permission Result =====
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
