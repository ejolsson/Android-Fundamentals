package com.example.androidfundamentals

import Fight.FightFragment
import HeroList.HeroListFragment
import HeroList.HeroListViewModel
import Fight.FightViewModel
import Fight.listOfHeroesSample
import HeroList.HeroCellAdapter
import HeroList.HeroClicked
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfundamentals.databinding.HeroActivityMainBinding
import com.example.androidfundamentals.databinding.HeroCell2Binding

class HeroMainActivity : AppCompatActivity(), HeroClicked {

    private lateinit var heroActivityMainBinding: HeroActivityMainBinding // 1st xml UI setup
    private lateinit var cellBinding: HeroCell2Binding // cell setup
    private val heroListViewModel: HeroListViewModel by viewModels()
    private val fightViewModel: FightViewModel by viewModels()
//    private val hero: Hero.Hero = TODO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        heroActivityMainBinding = HeroActivityMainBinding.inflate(layoutInflater)
        setContentView(heroActivityMainBinding.root) // initialize UI
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityMainBinding.fFragment.id, HeroListFragment(this))
            .commitNow()
            //.fFragment = FrameLayout placeholder

        // https://stackoverflow.com/questions/44301301/android-how-to-achieve-setonclicklistener-in-kotlin
        heroActivityMainBinding.heroListTitle.setOnClickListener {
            // thing to click and get something, based on Fragments Proj
            addHeroListFragment()
        }
//        lifecycleScope.launch {
//            heroListViewModel.uiState.collect { // uiState was just '.state.' Updated when bringing in API code
//                // Todo: launch adapters
//            }
//        }
        val listOfHeroesSample = listOfHeroesSample // sample data TODO: Remove
        val adapter = HeroCellAdapter(listOfHeroesSample, this) // instantiate adapter. Missing piece?????

        heroActivityMainBinding.
    }

    private fun addHeroListFragment() { // based on Fragments proj
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityMainBinding.fFragment.id, HeroListFragment(this)) // add heroClicked to class
            .commitNow()
        // listOfHeros is the RecyclerView
    }

    // Call supportFragmentManager for each element?
    private fun addFightFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityMainBinding.fFragment.id, FightFragment(this))
            .commitNow()
    }

    fun onHeroClicked(hero: Fight.Hero) {
        // Todo: define click functionality
    }
}