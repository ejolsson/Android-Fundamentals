package com.example.androidfundamentals.home.herolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    private lateinit var binding: HeroListFragmentBinding // UI
    private val viewModel: SharedViewModel by viewModels() // 1st VM, ensure HeroListVM is typed as viewModel()
    val adapter = HeroCellAdapter(viewModel.heroesFight, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroListFragmentBinding.inflate(inflater)
        Log.w("Tag", "HeroListFrag > onCreateView...")
        setObservers()
//        viewModel.fetchHeroes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchHeroes()

        // TODO: Remove use of sample data below
//        val heroesToShow = listOfHeroesSample
//        val adapter = HeroCellAdapter(heroesToShow, this) // GTG, instantiate adapter, send List<Hero>

        // check hero data right before
        Log.w("Tag", "HeroListFrag > onViewCreated > heroesFight = ${viewModel.heroesFight}") // empty, data not ready from API at this point of code execution
//
        val heroesToShow = viewModel.heroesFight
//        val adapter = HeroCellAdapter(heroesToShow, this) // instantiate adapter, send List<Hero>

        binding.rvListOfHeroes.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvListOfHeroes.adapter = adapter

    } // load data in adapters

    private fun setObservers() { // listen for async events, do some action
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.heroState.collect {
                when(it) {
                    is SharedViewModel.HeroState.OnHeroReceived -> {
                        Log.w("Tag", "HeroListFrag > onViewCreated > heroesFight = ${viewModel.heroesFight}") // empty

                        // TODO update UI. Things I've tried:
//                        onViewCreated().heroesToShow = viewModel.heroesFight
//                        binding.rvListOfHeroes.layoutManager.relaunch
//                        adapter.reload or refresh...

                    }
                    is SharedViewModel.HeroState.ErrorJSON -> Log.w("Tag", "HeroState ErrorJSON")
                    is SharedViewModel.HeroState.ErrorResponse -> Log.w("Tag", "HeroState ErrorResponse")
                    is SharedViewModel.HeroState.Idle -> Unit
                }
            }
        }
    }

    override fun heroSelectionClicked(hero: Hero) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fFragment, FightFragment(hero))
            .addToBackStack(HeroActivity::javaClass.name)
            .commit()
    } // send clicked hero to battle simulation
}