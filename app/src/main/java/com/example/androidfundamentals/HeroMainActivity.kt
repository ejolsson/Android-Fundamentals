package com.example.androidfundamentals

import Fight.FightViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidfundamentals.databinding.HeroFightBinding
import kotlinx.coroutines.launch

class HeroMainActivity : AppCompatActivity() {

    private lateinit var binding : HeroFightBinding

    private val viewModel : FightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HeroFightBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initListener() {
        binding.takeDamageButton.setOnClickListener() {
            viewModel.takeDamage()
        }
    }

    private fun initCollects() {
        lifecycleScope.launch {
            viewModel.uiState.collect{
                Log.w("Tag", "Life updated")
                when(it) { // must list our all 3 UiResponse cases
                    is FightViewModel.UiResponse.Started -> binding.lifeBarLabel.text = "100%"
                    is FightViewModel.UiResponse.LifeRemaining -> binding.lifeBarLabel.text = it.life.toString()
                }
            }
        }
    }
}