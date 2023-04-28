package Fight

import HeroList.HeroDTO
import HeroList.HeroListViewModel
import Login.LoginViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import Fight.Hero

class FightViewModel : ViewModel() {

    // model file using ViewModelAdvanced project

    private val _uiState = MutableStateFlow<UiResponse>(UiResponse.Started())
    val uiState: StateFlow<UiResponse> = _uiState

    val goku = Hero("GoKu", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300")
    private var life:Double = 100.0
    private var damageLevels = 6

    val randomNumber = Random.nextInt(1,damageLevels)

    fun heal(life:Int) = life + 20

    fun takeDamage() = run {
        viewModelScope.launch {
            life -= 0.1 * randomNumber
            _uiState.value = UiResponse.LifeRemaining(life)
        }
//        _uiState.value = UiResponse.Ended()
    }

    sealed class UiResponse {

        data class LifeRemaining(var life: Double): UiResponse()

//        class Ended() : UiResponse()

        class Started() : UiResponse()
    }
}