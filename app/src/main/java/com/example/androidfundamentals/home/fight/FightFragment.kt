package com.example.androidfundamentals.home.fight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidfundamentals.R
import com.example.androidfundamentals.data.Hero
import com.example.androidfundamentals.databinding.HeroFightFragmentBinding
import com.example.androidfundamentals.home.SharedViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

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
        Log.w("Tag", "FightFrag > onCreateView")
        setObservers()
//        var life: Int = 100
//        binding.lifebar.progress = life
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fightButton = getView()?.findViewById<Button>(R.id.fight_button)
        val takeDamageButton = getView()?.findViewById<Button>(R.id.take_damage_button)
        val healButton = getView()?.findViewById<Button>(R.id.heal_button)

        fightButton?.setOnClickListener {
//            Log.w("Tag", "fightButton tapped")
            viewModel.fight()
        }
        takeDamageButton?.setOnClickListener {
//            Log.w("Tag", "takeDamageButton tapped")
            viewModel.takeDamage()
        }
        healButton?.setOnClickListener {
//            Log.w("Tag", "healButton tapped")
            viewModel.heal()
        }

    }

    private fun setObservers() { // listen for events, do some action
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.heroState.collect {
                when (it) {
                    is SharedViewModel.HeroState.OnHeroReceived -> {
                        Log.w("Tag", "When... is.. SharedViewModel.HeroState.OnHeroReceived - It's a fight!")
                    }
                    is SharedViewModel.HeroState.Idle -> Unit
                }
            }
        }
    }
}