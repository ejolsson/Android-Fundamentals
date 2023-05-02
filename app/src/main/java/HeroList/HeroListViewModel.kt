package HeroList

import Fight.Hero
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

// ?? Should HeroMainActivity and HeroListFragment have separate viewModels?
class HeroListViewModel: ViewModel() {

    // TODO API call

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState
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
                        Hero(it.name, it.photo) })
                } catch (ex: Exception) {
                    _uiState.value= UiState.Error("Something went wrong in the response")
                }
            } ?: run { _uiState.value = UiState.Error("Something went wrong in the request") }
        }

    }

    sealed class UiState {
        object Idle: UiState()
        data class Error(val error: String) : UiState()
        data class OnHeroReceived(val name: List<Hero>) : UiState()

    }
}