package com.example.androidfundamentals.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.androidfundamentals.R
import com.example.androidfundamentals.databinding.LoginBinding
import com.example.androidfundamentals.home.HeroMainActivity
import com.example.androidfundamentals.home.SharedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginMainActivity : AppCompatActivity() {
    private val viewModel : LoginViewModel by viewModels() // L5, 0.35.00, extra lib needed
    private lateinit var binding : LoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.w("Tag", "onCreate")

        // better to use: viewLifecycleOwner.lifecycleScope.launch {...???
        lifecycleScope.launch {
            viewModel.uiState.collect(){
                when(it){
                    is LoginViewModel.UiState.OnLoginReceived -> Log.w("Tag", "Login success, token = $tokenPublic")
                    is LoginViewModel.UiState.Error -> Log.w("Tag", "Error")
                    is LoginViewModel.UiState.Idle -> Unit
                }
            }
        }

        // Rapid login for testing
//        val email = "ejolsson@gmail.com" // todo: Remove. Testing only.
//        val password = "vamosRafa2023!" // todo: Remove. Testing only.

        // connect UI to usable fields
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.bLogin)
//        var token: String = "" // using tokenPublic instead

        loginButton.setOnClickListener {
            Log.w("Tag","Login button tapped")
//            Log.w("Tag", "email = ${email.text}")
//            Log.w("Tag", "password = ${password.text}")
            tokenPublic = viewModel.userLogin("${email.text}", "${password.text}")
//            tokenPublic = viewModel.userLogin(email, password) // todo: Remove. For fast login only
            Log.w("Tag", "token post api call = $tokenPublic") // empty
            HeroMainActivity.launch(this, token = tokenPublic)
        }
    }




    // Persistence - Optional


//    private fun loadFromPreferences() {
//        getPreferences(Context.MODE_PRIVATE).apply {
//            Log.w("Tag","")
//        }
//    }
//
//    override fun onStop() {
//        saveFromPreferences()
//        super.onStop()
//    }
//
    // fm proj Lifecycle & SharedPreferences, see Discord comment "For example, fun saveOnSharedPreferences(context: Context, value: String)."
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        Log.w("Tag", "onSaveInstanceState")
//        outState.putInt(tagInt, counter)
//        outState.putString(token, viewModel.token)
//
//    }

}