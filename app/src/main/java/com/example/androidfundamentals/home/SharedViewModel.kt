package com.example.androidfundamentals.home

import android.util.Log
import com.example.androidfundamentals.data.Hero
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfundamentals.data.HeroDTO
import com.example.androidfundamentals.databinding.HeroFightFragmentBinding
import com.example.androidfundamentals.home.fight.FightFragment
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
    private lateinit var binding: HeroFightFragmentBinding
    private lateinit var hero: Hero
    lateinit var fightFragment: FightFragment

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

    fun selectHero(hero: Hero) { // called in HeroListFrag > fun heroSelectionClicked
        _heroListState.value = HeroListState.OnHeroSelected(hero)
        _heroState.value = HeroState.OnHeroReceived(hero)
    }

    private var life:Int = 100
    private var damageLevels = 6

    private fun generateRandomNumber(levels: Int): Int {
        return Random.nextInt(1, levels)
    }

    fun takeDamage(lifeBefore: Int):Int {

        Log.w("Tag", "fun takeDamage...")

        val damage = generateRandomNumber(damageLevels)

        val lifeAfter = lifeBefore - 10 * damage

        Log.w("Tag", "Damage = -${damage}0, life = $lifeAfter")

        return lifeAfter
    }

    fun heal(lifeBefore: Int):Int {

        Log.w("Tag", "fun heal...")

        val lifeAfter = lifeBefore + 20

        Log.w("Tag", "Healng = +20, Life = $lifeAfter")

        return lifeAfter
    }

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