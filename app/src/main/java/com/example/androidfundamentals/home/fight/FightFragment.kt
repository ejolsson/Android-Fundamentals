package com.example.androidfundamentals.home.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.androidfundamentals.data.Hero
import com.example.androidfundamentals.databinding.HeroFightFragmentBinding
import com.example.androidfundamentals.home.SharedViewModel
import com.squareup.picasso.Picasso

class FightFragment(private val hero: Hero) : Fragment() {

    private lateinit var binding: HeroFightFragmentBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroFightFragmentBinding.inflate(inflater)
        binding.heroFightPage.text = hero.name
        Picasso.get().load(hero.photo).into(binding.heroBattlePic)
        return binding.root
    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.root.setOnClickListener {
//            // Callback methods, like fetchHeroes. Should this go under viewModel?
//            // callback.onClick
//        }
//    }

//    private fun initListener() {
//        binding.takeDamageButton.setOnClickListener() {
//            viewModel.takeDamage()
//        }
//    }
//
//    private fun initCollects() {
//        lifecycleScope.launch {
//            viewModel.uiState.collect{
//                Log.w("Tag", "Life updated")
//                when(it) { // must list our all 3 UiResponse cases
//                    is FightViewModel.UiResponse.Started -> binding.lifeBarLabel.text = "100%"
//                    is FightViewModel.UiResponse.LifeRemaining -> binding.lifeBarLabel.text = it.life.toString()
//                }
//            }
//        }
//    }
}