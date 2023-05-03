package com.example.androidfundamentals.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.androidfundamentals.home.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SharedViewModel.UiState>(SharedViewModel.UiState.Idle)

//    private val userName: String = ""
//    private val password: String = ""

//    init {
//        login(
//        )
//    }
    fun userLogin (email: String, password: String): String {
        Log.w("Tag","fun userLogin started...")

        // Create url string


        // Create url request
        // Make request
        // Change/navigate UI post login
        // Return token
        return "eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJlbWFpbCI6ImVqb2xzc29uQGdtYWlsLmNvbSIsImlkZW50aWZ5IjoiQjgzQjU2NkItRjY0Qi00Q0Y2LUJDMjUtQjBBMDE2RDM2QjMyIiwiZXhwaXJhdGlvbiI6NjQwOTIyMTEyMDB9.t1oOVWkuT7BmUBIqnTKBVVEodoq73hh2JLrQYx8cf6c"
    }

}