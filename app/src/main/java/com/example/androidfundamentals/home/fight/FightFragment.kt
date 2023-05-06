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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

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
        binding.ivDamageAnimation.alpha = damageTint // initialized to 0.0F
        Picasso.get().load(hero.photo).into(binding.ivHeroFightPic)
        Log.w("Tag FightFrag", "FightFrag > onCreateView ********")
        setObservers()
//        var life: Int = 100
//        binding.lifebar.progress = life
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fightButton = getView()?.findViewById<Button>(R.id.fight_button)
        val takeDamageButton = getView()?.findViewById<Button>(R.id.take_damage_button)
        val healButton = getView()?.findViewById<Button>(R.id.heal_button)

        Log.w("Tag FightFrag", "Hero info: $hero")
        Log.w("Tag FightFrag", "${hero.name} life: ${hero.life}")

        fightButton?.setOnClickListener {
//            Log.w("Tag", "fightButton tapped")
            viewModel.fight()
        }
        takeDamageButton?.setOnClickListener {
            Log.w("Tag FightFrag", "takeDamageButton tapped")
            Log.w("Tag FightFrag", "Hero info: $hero")
            Log.w("Tag FightFrag", "${hero.name} life: ${hero.life}")
            viewModel.takeDamage()
            binding.lifeBarLabel.text = "Life: ${hero.life.toString()}"
            binding.ivDamageAnimation.alpha = 0.8F
            binding.ouch.alpha = 1.0F
//            delay(300)
//            sleep(1000)
//            binding.ivDamageAnimation.alpha = 0.0F
        }
        healButton?.setOnClickListener {
//            Log.w("Tag", "healButton tapped")
            viewModel.heal()
            binding.ivDamageAnimation.alpha = 0.0F
            binding.ouch.alpha = 0.0F
        }

    }

    private fun setObservers() { // listen for events, do some action
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.heroState.collect {
                when (it) {
                    is SharedViewModel.HeroState.OnHeroReceived -> {
                        Log.w("Tag FightFrag", "When > is > SharedVM.HeroState.OnHeroReceived")
                        hero.life = 100
                        Log.w("Tag FightFrag", "Hero info: $hero")
                        Log.w("Tag FightFrag", "${hero.name} life: ${hero.life}")
//                        parentFragmentManager.beginTransaction()
//                            .replace(R.id.fFragment, HeroListFragment())
//                            .addToBackStack(HeroListFragment::javaClass.name)
//                            .commit()
                    }
                    is SharedViewModel.HeroState.Idle -> Unit
                }
            }
        }
    }
}