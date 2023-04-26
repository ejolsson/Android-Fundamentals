package com.example.androidfundamentals

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ViewModelMainActivity : ViewModel() {

    // DataModel
    data class Hero(val name: String, val description: String, val photo: String) {
        var life: Int = 100
    }

    // sample data
    val listOfHeroes = listOf(
        Hero("Goku", "I am Goku", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300"),
        Hero("Vegeta","I am Vegeta", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/vegetita.jpg?width=300"),
        Hero("Bulma","I am Bulma", "https://cdn.alfabetajuega.com/alfabetajuega/2021/01/Bulma-Dragon-Ball.jpg?width=300")
    )

    // Battle Logic (all calculations take place in ViewModel)
    var life:Int = 100
    private var damageLevels = 6

    fun heal(life:Int) = life + 20

    fun takeDamage() = {
        val randomNumber = Random.nextInt(1,damageLevels)
    }
}