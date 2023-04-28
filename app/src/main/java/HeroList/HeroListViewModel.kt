package HeroList

import Fight.Hero

class HeroListViewModel {

    // TODO API call

    val herosToMap = listOfHeroesDTO

    var heroesFight = herosToMap.map { hero -> hero }

    sealed class UiState {
        class Idle : UiState()
        data class Error(val string: String) : UiState()
        data class Success(val bootcampList: List<Hero>) : UiState()

    }
}