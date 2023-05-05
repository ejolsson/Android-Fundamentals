package com.example.androidfundamentals.home

import android.util.Log
import com.example.androidfundamentals.data.Hero
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfundamentals.data.HeroDTO
import com.example.androidfundamentals.login.tokenPublic
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

class SharedViewModel: ViewModel() {

    private val _heroState = MutableStateFlow<HeroState>(HeroState.Idle)
    val heroState: StateFlow<HeroState> = _heroState

    private val selectedHero: Hero? = null
    private val token = "eyJraWQiOiJwcml2YXRlIiwidHlwIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJjZHRsQHBydWVibWFpbC5lcyIsImlkZW50aWZ5IjoiRDIwRTAwQTktODY0NC00MUYyLUE0OUYtN0ZDRUY2MTVFMTQ3In0.wMqJfh5qcs5tU6hu2VxT4OV9Svd7BGBA7HsVpKhx5-8"

    var heroesFight: List<Hero> = listOf() // initialize blank

    fun fetchHeroes() {
        Log.w("Tag", "fetchHeroes...")
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val baseUrl = "https://dragonball.keepcoding.education/api/" // Todo: pull out
            val url = "${baseUrl}heros/all"
            val body = FormBody.Builder()
                .add("name", "") // "" asks for all heroes
                .build()
            Log.w("Tag","tokenPublic used for fetchHeroes = $tokenPublic")
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body?.let { responseBody ->
                val gson = Gson()
                try {
                    val heroDtoArray = gson.fromJson(responseBody.string(), Array<HeroDTO>::class.java)
                    Log.w("Tag", "heroDtoArray.size = ${heroDtoArray.toList()}") // GTG
                    Log.w("Tag", "heroDtoArray.asList = ${heroDtoArray.asList()}") // GTG

                    heroesFight = heroDtoArray.toList().map { Hero(it.name, it.photo) } // map API data to local model for simulation
                    Log.w("Tag", "Shared ViewModel > getHeroes() > heroesFight = $heroesFight")

                    _heroState.value = HeroState.OnHeroReceived(heroDtoArray.toList().map {
                        Hero(it.name, it.photo)
                    })
                } catch (ex: Exception) {
                    _heroState.value= HeroState.ErrorJSON("Something went wrong in the fetchHeroes response")
                }
            } ?: run { _heroState.value = HeroState.ErrorResponse("Something went wrong in the fetchHeroes request") }
            Log.w("Tag", "SharedVM > getHeroes L65 > heroesFight = $heroesFight")
        }

    }

    val goku = Hero("GoKu", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300")
    private var life:Double = 100.0
    private var damageLevels = 6

    val randomNumber = Random.nextInt(1,damageLevels)

    fun heal(life:Int) = life + 20

//    fun takeDamage() = run {
//        viewModelScope.launch {
//            life -= 0.1 * randomNumber
//            _heroState.value = HeroState.LifeRemaining(life)
//        }
//        _heroState.value = UiResponse.Ended()
//    }

    sealed class HeroState {
        object Idle: HeroState()
        data class OnHeroReceived(val heroes: List<Hero>): HeroState()
        data class ErrorJSON(val error: String): HeroState()
        data class ErrorResponse(val error: String): HeroState()
    }
}