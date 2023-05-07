package com.example.androidfundamentals.test.home

import com.example.androidfundamentals.home.SharedViewModel
import com.example.androidfundamentals.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    val sharedViewModel = SharedViewModel()
    val loginViewModel = LoginViewModel()

    @Test
    fun `Try 60 + 20`() {
        val result = sharedViewModel.heal(60)
        val expectedValue = 80
        assertEquals(expectedValue, result)
    }

    @Test
    fun `Try 20 + 20`() {
        val result = sharedViewModel.heal(20)
        val expectedValue = 40
        assertEquals(expectedValue, result)
    }

    @Test
    fun `Damage between 10 and 60`() {
        val lifeAfter = sharedViewModel.takeDamage(100)
        assertTrue(lifeAfter >= 40)
        assertTrue(lifeAfter <= 90)
    }

//    @Test
//    fun `hero api test`() = runTest {
//
//        val token = "eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJlbWFpbCI6ImVqb2xzc29uQGdtYWlsLmNvbSIsImlkZW50aWZ5IjoiQjgzQjU2NkItRjY0Qi00Q0Y2LUJDMjUtQjBBMDE2RDM2QjMyIiwiZXhwaXJhdGlvbiI6NjQwOTIyMTEyMDB9.t1oOVWkuT7BmUBIqnTKBVVEodoq73hh2JLrQYx8cf6c"
//
//        val heroesFight = listOf(
//            Hero("Goku", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300", 100, 100),
//            Hero("Vegata", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/vegetita.jpg?width=300", 100, 0))
//
//        launch {
//            viewModel.heroesLiving. {
//                when(it) {
//                    is SharedViewModel.HeroListState.OnHeroListReceived -> listOf(assert(heroesFight == it))
//                    is SharedViewModel.HeroListState.OnHeroSelected
//                    is SharedViewModel.HeroListState.ErrorResponse -> assertEquals(error() = false)
//                    is SharedViewModel.HeroListState.ErrorJSON
//                }
//            }
//            viewModel.fetchHeroes(token)
//        }
}