package com.example.androidfundamentals.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfundamentals.home.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request

var tokenPublic: String = ""

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle) // initialize state to Idle
    val uiState: StateFlow<UiState> = _uiState

    var token: String = "" // remove in favor of tokenGobal?

//    init {
//        userLogin()
//    }

    fun userLogin(email: String, password: String): String {
    Log.w("Tag","fun userLogin started...")
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            // https://dragonball.keepcoding.education/api/auth/login
            val baseUrl = "https://dragonball.keepcoding.education/api/" // Todo: pull out
            val url = "${baseUrl}auth/login"
            val credentials = Credentials.basic(email, password)
            val formBody = FormBody.Builder().build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization",credentials)
                .post(formBody)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            println(response.body)

            response.body?.let { responseBody ->
                tokenPublic = responseBody.string() // had 'token' before
                Log.w("Tag","Login successful. tokenPublic = ${tokenPublic}")
                _uiState.value= UiState.OnLoginReceived(tokenPublic) // had responseBody.string()
            } ?: run { _uiState.value = UiState.Error("Something went wrong in the request") }

        }
        // Change/navigate UI post login
        return token
    }

    sealed class UiState {
        object Idle: UiState()
        data class Error(val error: String): UiState()
        data class OnLoginReceived(val text: String): UiState()
    }
}














