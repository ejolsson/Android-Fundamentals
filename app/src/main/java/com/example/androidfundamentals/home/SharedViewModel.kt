package com.example.androidfundamentals.home

import com.example.androidfundamentals.data.Hero
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfundamentals.data.HeroDTO
import com.example.androidfundamentals.data.listOfHeroesDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.random.Random

// ?? Should HeroMainActivity and HeroListFragment have separate viewModels?
class SharedViewModel: ViewModel() {

    // TODO API call

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
//    val uiState: StateFlow<UiState> = _uiState

    private val selectedHero: Hero? = null
//    var state: StateFlow<String> = _state
private val token = "eyJraWQiOiJwcml2YXRlIiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJjZHRsQHBydWVibWFpbC5lcyIsImlkZW50aWZ5IjoiRDIwRTAwQTktODY0NC00MUYyLUE0OUYtN0ZDRUY2MTVFMTQ3In0.wMqJfh5qcs5tU6hu2VxT4OV9Svd7BGBA7HsVpKhx5-8"

    val herosToMap = listOfHeroesDTO
    var heroesFight = herosToMap.map { hero -> hero }

    init {
        getHeroes()
    }

    fun relaunch() {
        getHeroes()
    }

    fun getHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/heros/all"
            val body = FormBody.Builder()
                .add("name", "")
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            println(response.body)
            response.body?.let { responseBody ->
                val gson = Gson()
                try {
                    val heroDtoArray = gson.fromJson(responseBody.string(), Array<HeroDTO>::class.java)
                    _uiState.value = UiState.OnHeroReceived(heroDtoArray.toList().map {
                        Hero(it.name, it.photo)
                    })
                } catch (ex: Exception) {
                    _uiState.value= UiState.Error("Something went wrong in the response")
                }
            } ?: run { _uiState.value = UiState.Error("Something went wrong in the request") }
        }

    }

    val uiState: StateFlow<UiState> = _uiState

    val goku = Hero("GoKu", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300")
    private var life:Double = 100.0
    private var damageLevels = 6

    val randomNumber = Random.nextInt(1,damageLevels)

    fun heal(life:Int) = life + 20

    fun takeDamage() = run {
        viewModelScope.launch {
            life -= 0.1 * randomNumber
            _uiState.value = UiState.LifeRemaining(life)
        }
//        _uiState.value = UiResponse.Ended()
    }

    sealed class UiState {
        object Idle: UiState()
        data class Error(val error: String) : UiState()
        data class OnHeroReceived(val name: List<Hero>) : UiState()

        data class LifeRemaining(var life: Double): UiState()

    }
}