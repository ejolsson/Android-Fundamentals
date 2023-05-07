package com.example.androidfundamentals.home.herolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfundamentals.R
import com.example.androidfundamentals.data.Hero
import com.example.androidfundamentals.databinding.HeroListFragmentBinding
import com.example.androidfundamentals.home.HeroActivity
import com.example.androidfundamentals.home.SharedViewModel
import com.example.androidfundamentals.home.fight.FightFragment
import kotlinx.coroutines.launch

class HeroListFragment(): Fragment(), HeroClicked {

    private lateinit var binding: HeroListFragmentBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: HeroCellAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroListFragmentBinding.inflate(inflater)
        Log.w("Tag", "HeroListFrag > onCreateView...")
        setObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() { // listen for async events, do some action
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.heroListState.collect { // -> it
                when(it) {
                    is SharedViewModel.HeroListState.OnHeroListReceived -> {
                        Log.w("Tag HeroListFrag", ".OnHeroListReceived")
                        Log.w("Tag HeroListFrag", "HeroListFrag > onViewCreated > heroesFight = ${it.heroes}") // empty

                        it.heroes[0].currentLife = 0 // filter test... works!!

                        adapter = HeroCellAdapter(it.heroes.filter { it.currentLife > 0 }, this@HeroListFragment) // instantiate adapter, send List<Hero>
                        binding.rvListOfHeroes.layoutManager = LinearLayoutManager(binding.root.context)
                        binding.rvListOfHeroes.adapter = adapter
                    }
                    is SharedViewModel.HeroListState.OnHeroDeath -> {
                        Log.w("Tag HeroListFrag", ".OnHeroDeath")
                        adapter = HeroCellAdapter(it.heroes.filter { it.currentLife > 0 }, this@HeroListFragment) // instantiate adapter, send List<Hero>
                        binding.rvListOfHeroes.layoutManager = LinearLayoutManager(binding.root.context)
                        binding.rvListOfHeroes.adapter = adapter
                        Log.w("Tag HeroListFrag", "HeroListFrag > is ....OnHero Death > heroesLiving = ${viewModel.heroesLiving}")
                    }
                    is SharedViewModel.HeroListState.ErrorJSON ->
                        Log.w("Tag HeroListFrag", "HeroState ErrorJSON")
                    is SharedViewModel.HeroListState.ErrorResponse ->
                        Log.w("Tag HeroListFrag", "HeroState ErrorResponse")
                    is SharedViewModel.HeroListState.Idle -> Unit
                    is SharedViewModel.HeroListState.OnHeroSelected -> {
                        Log.w("Tag HeroListFrag", ".OnHeroSelected")
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fFragment, FightFragment(it.hero))
                            .addToBackStack(HeroActivity::javaClass.name) // "back button" goes back to login
                            .commit()
                    }
                }
            }
        }
    }

    override fun heroSelectionClicked(hero: Hero) {
        viewModel.selectHero(hero)
    } // send clicked hero to battle simulation
}