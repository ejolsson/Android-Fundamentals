package com.example.androidfundamentals.test.home

import com.example.androidfundamentals.home.SharedViewModel
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

//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
    val viewModel = SharedViewModel()
    @Test
    fun `Try 60 + 20`() {
        val result = viewModel.heal(60)
        val expectedValue = 80
        assertEquals(expectedValue, result)
    }

    @Test
    fun `Try 20 + 20`() {
        val result = viewModel.heal(20)
        val expectedValue = 40
        assertEquals(expectedValue, result)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}