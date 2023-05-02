package com.example.androidfundamentals

import com.example.androidfundamentals.fight.FightFragment
import com.example.androidfundamentals.herolist.HeroListFragment
import com.example.androidfundamentals.herolist.HeroListViewModel
import com.example.androidfundamentals.fight.FightViewModel
import com.example.androidfundamentals.fight.Hero
import com.example.androidfundamentals.fight.listOfHeroesSample
import com.example.androidfundamentals.herolist.HeroCellAdapter
import com.example.androidfundamentals.herolist.HeroClicked
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfundamentals.databinding.HeroActivityMainBinding
import com.example.androidfundamentals.databinding.HeroCellBinding
import com.example.androidfundamentals.databinding.HeroListFragmentBinding

class HeroMainActivity : AppCompatActivity(), HeroClicked {

    // TODO: manage all these bindings here??
    private lateinit var heroActivityMainBinding: HeroActivityMainBinding // 1st xml UI setup
    private lateinit var heroListFragmentBinding: HeroListFragmentBinding
    private lateinit var cellBinding: HeroCellBinding // cell setup

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
        val adapter = HeroCellAdapter(listOfHeroesSample, this) // instantiate adapter.

        // TODO: Runtime crash when using 2 lines below. Try in HeroListFragment > onCreateView
//        heroListFragmentBinding.rvListOfHeroes.layoutManager = LinearLayoutManager(this) // runtime crash
//        heroListFragmentBinding.rvListOfHeroes.adapter = adapter

//        heroListFragmentBinding.rvListOfHeroes.setOnClickListener {
//            addFightFragment()
//        }

//        heroListViewModel.getHeroes() // runtime crash
    }

    private fun addHeroListFragment() { // based on Fragments proj
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityMainBinding.fFragment.id, HeroListFragment(this)) // add heroClicked to class
            .commitNow()
        // listOfHeros is the RecyclerView
    }

    private fun addFightFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityMainBinding.fFragment.id, FightFragment(this))
            .commitNow()
    }

    fun onHeroClicked(hero: Hero) {
        // Todo: define click functionality
        addFightFragment()
    }

}