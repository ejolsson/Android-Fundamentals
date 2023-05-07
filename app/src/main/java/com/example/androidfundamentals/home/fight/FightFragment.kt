package com.example.androidfundamentals.home.fight

import android.annotation.SuppressLint
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
    private var damageTint: Float = 0.0F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroFightFragmentBinding.inflate(inflater)
        binding.heroFightPage.text = hero.name
        binding.ivDamageAnimation.alpha = damageTint
        Picasso.get().load(hero.photo).into(binding.ivHeroFightPic)
        Log.w("Tag FightFrag", "FightFrag > onCreateView ********")
        setObservers()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val takeDamageButton = getView()?.findViewById<Button>(R.id.take_damage_button)
        val healButton = getView()?.findViewById<Button>(R.id.heal_button)

        Log.w("Tag FightFrag", "${hero.name} life: ${hero.currentLife}")

        takeDamageButton?.setOnClickListener {

            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")

            hero.currentLife = viewModel.takeDamage(hero.currentLife)

            binding.lifeBarLabel.text = "Life ${hero.currentLife}"
            binding.lifebar.setProgress(hero.currentLife,true)
            binding.ivDamageAnimation.alpha = 0.4F
            binding.ouch.alpha = 1.0F

            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")
            if (hero.currentLife <= 0)  {
                Log.w("Tag FightFrag", "Life <= 0")
                viewModel.returnToHeroList()
            }
        }

        healButton?.setOnClickListener {

            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")

            hero.currentLife = viewModel.heal(hero.currentLife)

            binding.lifeBarLabel.text = "Life ${hero.currentLife}"
            binding.lifebar.setProgress(hero.currentLife,true)
            binding.ivDamageAnimation.alpha = 0.0F
            binding.ouch.alpha = 0.0F

            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.heroState.collect {
                when (it) {
                    is SharedViewModel.HeroState.OnHeroReceived -> {
                        Log.w("Tag FightFrag", ".OnHeroReceived")
                        Log.w("Tag FightFrag", "${hero.name} life: ${hero.currentLife}")
                    }
                    is SharedViewModel.HeroState.HeroLifeZero -> {
                        Log.w("Tag FightFrag", ".HeroLifeZero")
                        Log.w("Tag FightFrag", "Shared ViewModel > getHeroes() > heroesLiving = ${viewModel.heroesLiving}")
                        activity?.supportFragmentManager?.popBackStack()
                    }
                    is SharedViewModel.HeroState.Idle -> Unit
                }
            }
        }
    }
}