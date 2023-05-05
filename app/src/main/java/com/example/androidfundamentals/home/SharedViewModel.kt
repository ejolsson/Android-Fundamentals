package com.example.androidfundamentals.home

import android.util.Log
import com.example.androidfundamentals.data.Hero
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfundamentals.data.HeroDTO
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

    private val _heroListState = MutableStateFlow<HeroListState>(HeroListState.Idle)
    val heroListState: StateFlow<HeroListState> = _heroListState
    private val _heroState = MutableStateFlow<HeroState>(HeroState.Idle)
    val heroState: StateFlow<HeroState> = _heroState

    fun fetchHeroes(token: String) {
        Log.w("Tag", "fetchHeroes...")
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val baseUrl = "https://dragonball.keepcoding.education/api/" // Todo: pull out
            val url = "${baseUrl}heros/all"
            val body = FormBody.Builder()
                .add("name", "")
                .build()
            Log.w("Tag","tokenPublic used for fetchHeroes = $token")
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            if (response.isSuccessful) {
                response.body?.let { responseBody ->
                    val gson = Gson()
                    try {
                        val response = responseBody.string()
                        val heroDtoArray = gson.fromJson(response, Array<HeroDTO>::class.java)
                        Log.w("Tag", "heroDtoArray.asList = ${heroDtoArray.asList()}")
                        val heroesFight = heroDtoArray.toList().map { Hero(it.name, it.photo) } // map API data to local model for simulation
                        Log.w("Tag", "Shared ViewModel > getHeroes() > heroesFight = $heroesFight")
                        _heroListState.value = HeroListState.OnHeroListReceived(heroesFight)
                    } catch (ex: Exception) {
                        _heroListState.value= HeroListState.ErrorJSON("Something went wrong in the fetchHeroes response")
                    }
                } ?: run { _heroListState.value = HeroListState.ErrorResponse("Something went wrong in the fetchHeroes request") }
            }
            else {
                _heroListState.value = HeroListState.ErrorResponse("Something went wrong in the fetchHeroes response")
            }
        }
    }

    fun selectHero(hero: Hero) {
        _heroListState.value = HeroListState.OnHeroSelected(hero)
        _heroState.value = HeroState.OnHeroReceived(hero)
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

    sealed class HeroListState {
        object Idle: HeroListState()
        data class OnHeroListReceived(val heroes: List<Hero>): HeroListState()
        data class OnHeroSelected(val hero: Hero): HeroListState()
        data class ErrorJSON(val error: String): HeroListState()
        data class ErrorResponse(val error: String): HeroListState()
    }
    sealed class HeroState {
        object Idle: HeroState()
        data class OnHeroReceived(val hero: Hero): HeroState()
    }
}