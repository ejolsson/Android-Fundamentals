package com.example.androidfundamentals.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.androidfundamentals.R
import com.example.androidfundamentals.databinding.LoginBinding
import com.example.androidfundamentals.home.HeroActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val viewModel : LoginViewModel by viewModels() // L5, 0.35.00, extra lib needed
    private lateinit var binding : LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.w("Tag", "onCreate")

        lifecycleScope.launch {
            viewModel.loginState.collect(){
                when(it){
                    is LoginViewModel.LoginState.OnLoginReceived -> Log.w("Tag", "Login success, token = $tokenPublic")
                    is LoginViewModel.LoginState.Error -> Log.w("Tag", "Error")
                    is LoginViewModel.LoginState.Idle -> Unit
                }
            }
        }



        // connect UI to usable fields
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.bLogin)

        loginButton.setOnClickListener {
            Log.w("Tag","Login button tapped")
            Log.w("Tag", "email = ${email.text}")
            Log.w("Tag", "password = ${password.text}")

            tokenPublic = viewModel.userLogin("${email.text}", "${password.text}")

            Log.w("Tag", "token post api call = $tokenPublic") // empty

            HeroActivity.launch(this, token = tokenPublic)
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