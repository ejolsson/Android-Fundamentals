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
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.androidfundamentals.R
import com.example.androidfundamentals.data.Hero
import com.example.androidfundamentals.data.listOfHeroesSample
import com.example.androidfundamentals.databinding.HeroFightFragmentBinding
import com.example.androidfundamentals.home.HeroActivity
import com.example.androidfundamentals.home.SharedViewModel
import com.example.androidfundamentals.home.herolist.HeroListFragment
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
        binding.ivDamageAnimation.alpha = damageTint // initialized to 0.0F
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

//        Log.w("Tag FightFrag", "Hero info: $hero")
        Log.w("Tag FightFrag", "${hero.name} life: ${hero.currentLife}")
//        Log.w("Tag FightFrag", "Living heroes (sample data): ${listOfHeroesSample.filter { it.currentLife > 0 }}") // works!
//        Log.w("Tag FightFrag", "Living heroes: ${listOfHeroesSample[1].}")

        takeDamageButton?.setOnClickListener {

            // initial
//            Log.w("Tag FightFrag", "FightFrag > takeDamageButton tapped")
//            Log.w("Tag FightFrag", "FightFrag > Hero info: $hero")
            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")

            // fight
            hero.currentLife = viewModel.takeDamage(hero.currentLife)

            binding.lifeBarLabel.text = "Life ${hero.currentLife}"
            binding.lifebar.setProgress(hero.currentLife,true)
            binding.ivDamageAnimation.alpha = 0.4F
            binding.ouch.alpha = 1.0F
//            delay(300)
//            sleep(1000)
//            binding.ivDamageAnimation.alpha = 0.0F

            // after
            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")
            if (hero.currentLife <= 0)  {
                Log.w("Tag FightFrag", "Life <= 0")
                viewModel.returnToHeroList()
            }
        }

        healButton?.setOnClickListener {

            // initial
            Log.w("Tag FightFrag", "FightFrag > healButton tapped")
            Log.w("Tag FightFrag", "FightFrag > Hero info: $hero")
            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")

            // heal
            hero.currentLife = viewModel.heal(hero.currentLife)

            binding.lifeBarLabel.text = "Life ${hero.currentLife}"
            binding.lifebar.setProgress(hero.currentLife,true) // show life value in progress bar
            binding.ivDamageAnimation.alpha = 0.0F
            binding.ouch.alpha = 0.0F

            // after
            Log.w("Tag FightFrag", "FightFrag > ${hero.name} life: ${hero.currentLife}")
        }

    }

    private fun setObservers() { // listen for events, do some action
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.heroState.collect {
                when (it) {
                    is SharedViewModel.HeroState.OnHeroReceived -> {
                        Log.w("Tag FightFrag", ".OnHeroReceived")
//                        hero.currentLife = 100
//                        Log.w("Tag FightFrag", "Hero info: $hero")
                        Log.w("Tag FightFrag", "${hero.name} life: ${hero.currentLife}")
//                        parentFragmentManager.beginTransaction()
//                            .replace(R.id.fFragment, HeroListFragment())
//                            .addToBackStack(HeroListFragment::javaClass.name)
//                            .commit()
                    }
                    is SharedViewModel.HeroState.HeroLifeZero -> {
                        Log.w("Tag FightFrag", ".HeroLifeZero")
                        Log.w("Tag FightFrag", "Shared ViewModel > getHeroes() > heroesLiving = ${viewModel.heroesLiving}")
                        // returns to login
                        activity?.supportFragmentManager?.popBackStack() //must have

                        // below returns to login, allows 1 more game, then crashes
//                        parentFragmentManager.commit {
//                            replace(R.id.fFragment, HeroListFragment())
//                            setReorderingAllowed(true)
//                            addToBackStack(HeroActivity::javaClass.name)
//                        }

                        // below crashes
//                        parentFragmentManager.beginTransaction()
//                            .add(R.id.fFragment, HeroListFragment())
//                            .addToBackStack(HeroActivity::javaClass.name)
//                            .commit()

                        // unresolved error
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.fFragment, HeroListFragment())
//                            .addToBackStack(HeroActivity::javaClass.name)
//                            .commit()

                        // below crashes
//                        childFragmentManager.beginTransaction()
//                            .replace(R.id.fFragment, HeroListFragment())
//                            .addToBackStack(HeroActivity::javaClass.name)
//                            .commit()

                    }
                    is SharedViewModel.HeroState.Idle -> Unit
                }
            }
        }
    }
}