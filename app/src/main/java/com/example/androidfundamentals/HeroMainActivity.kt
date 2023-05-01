package com.example.androidfundamentals

import Fight.FightFragment
import HeroList.HeroListFragment
import HeroList.HeroListViewModel
import Fight.FightViewModel
import HeroList.HeroClicked
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androidfundamentals.databinding.HeroActivityMainBinding
import com.example.androidfundamentals.databinding.HeroCell2Binding
import com.example.androidfundamentals.databinding.HeroListFragmentBinding
import com.example.androidfundamentals.databinding.HeroCellBinding
import kotlinx.coroutines.launch

class HeroMainActivity : AppCompatActivity(), HeroClicked {

    private lateinit var binding: HeroActivityMainBinding // 1st xml UI setup
    private lateinit var cellBinding: HeroCell2Binding // cell setup
    private val heroListViewModel: HeroListViewModel by viewModels()
    private val fightViewModel: FightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HeroActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // initialize UI
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fFragment.id, HeroListFragment(this))
            .commitNow()
            //.fFragment = FrameLayout placeholder

        // https://stackoverflow.com/questions/44301301/android-how-to-achieve-setonclicklistener-in-kotlin
        binding.heroListTitle.setOnClickListener {
            // thing to click and get something, based on Fragments Proj
            addHeroListFragment()
        }
//        lifecycleScope.launch {
//            heroListViewModel.uiState.collect { // uiState was just '.state.' Updated when bringing in API code
//                // Todo: launch adapters
//            }
//        }
    }

    private fun addHeroListFragment() { // based on Fragments proj
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fFragment.id, HeroListFragment(this)) // add heroClicked to class
            .commitNow()
        // listOfHeros is the RecyclerView
    }

    // Call supportFragmentManager for each element?
    private fun addFightFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fFragment.id, FightFragment(this))
            .commitNow()
    }

    fun onHeroClicked(hero: Fight.Hero) {
        // Todo: define click functionality
    }
}