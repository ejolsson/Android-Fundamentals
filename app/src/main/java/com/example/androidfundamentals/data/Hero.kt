package com.example.androidfundamentals.data

// Hero model copied from HeroDTO, used for battle simulation

data class Hero(
    val name: String,
    val photo: String,
    var life: Int = 100
) //{
//    var life: Int = 100 // life points
//}

// sample data
val listOfHeroesSample = listOf(
    Hero("Goku", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300", 100),
    Hero("Vegata", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/vegetita.jpg?width=300", 0),
    Hero("Bulma", "https://cdn.alfabetajuega.com/alfabetajuega/2021/01/Bulma-Dragon-Ball.jpg?width=300", -10),
    Hero("Krilin", "https://cdn.alfabetajuega.com/alfabetajuega/2020/08/Krilin.jpg?width=300", 0),
    Hero("Freezer", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/freezer-dragon-ball-bebe-abj.jpg?width=300", 20),
    Hero("Mr. Satán", "https://cdn.alfabetajuega.com/alfabetajuega/2020/06/dragon-ball-satan.jpg?width=300", 0)
)