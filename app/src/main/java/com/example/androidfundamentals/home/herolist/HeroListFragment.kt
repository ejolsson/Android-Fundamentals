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
            viewModel.heroListState.collect {
                when(it) {
                    is SharedViewModel.HeroListState.OnHeroListReceived -> {
                        Log.w("Tag", "HeroListFrag > onViewCreated > heroesFight = ${it.heroes}") // empty
                        adapter = HeroCellAdapter(it.heroes, this@HeroListFragment) // instantiate adapter, send List<Hero>
                        binding.rvListOfHeroes.layoutManager = LinearLayoutManager(binding.root.context)
                        binding.rvListOfHeroes.adapter = adapter
                    }
                    is SharedViewModel.HeroListState.ErrorJSON ->
                        Log.w("Tag", "HeroState ErrorJSON")
                    is SharedViewModel.HeroListState.ErrorResponse ->
                        Log.w("Tag", "HeroState ErrorResponse")
                    is SharedViewModel.HeroListState.Idle -> Unit
                    is SharedViewModel.HeroListState.OnHeroSelected -> {
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