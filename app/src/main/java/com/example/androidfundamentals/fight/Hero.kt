package com.example.androidfundamentals.fight

// Hero model copied from HeroDTO, used for battle simulation

data class Hero(
    val name: String,
    val photo: String
)

// sample data
val listOfHeroesSample = listOf(
    Hero("Goku", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300"),
    Hero("Vegata", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/vegetita.jpg?width=300"),
    Hero("Bulma", "https://cdn.alfabetajuega.com/alfabetajuega/2021/01/Bulma-Dragon-Ball.jpg?width=300")
)