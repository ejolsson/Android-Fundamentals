package com.example.androidfundamentals.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.androidfundamentals.R
import com.example.androidfundamentals.databinding.LoginBinding

class LoginMainActivity : AppCompatActivity() {

    private var counter = 0
    private var tagInt = "MyInteger"
    private var tagString = "MyString"

    private lateinit var binding : LoginBinding // all projects?

    private val viewModel : LoginViewModel by viewModels() // L5, 0.35.00, extra library needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
//        setContentView(R.layout.login)
//        setContentView(R.layout.hero_list)
//        setContentView(R.layout.hero_fight)
        setContentView(binding.root)
        Log.w("Tag", "onCreate")

        savedInstanceState?.let {
            counter = it.getInt(tagInt, 0)
            Log.w("Tag", it.getString(tagString).toString())
        }
        counter++
        Log.w("Tag", "counter = $counter")

        // connect UI to usable fields
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.bLogin)

        loginButton.setOnClickListener {
            Log.w("Tag","Login button tapped")
            Log.w("Tag", "email = ${email.text}")
            Log.w("Tag", "password = ${password.text}")
            viewModel.userLogin("${email.text}", "${password.text}")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.w("Tag", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w("Tag", "onResume")
    }

    override fun onPause() {
        Log.w("Tag", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.w("Tag", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.w("Tag", "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("Tag", "onRestart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.w("Tag", "onSaveInstanceState")
        outState.putInt(tagInt, counter)
        outState.putString(tagString, "El contador es $counter")

    }

    // TODO: - Pull image from internet -
    // https://www.tutorialspoint.com/how-do-i-load-an-imageview-by-url-on-android
}