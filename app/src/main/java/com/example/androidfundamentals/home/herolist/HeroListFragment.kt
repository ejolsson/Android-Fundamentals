package com.example.androidfundamentals.home.herolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfundamentals.R
import com.example.androidfundamentals.data.Hero
import com.example.androidfundamentals.databinding.HeroListFragmentBinding
import com.example.androidfundamentals.home.HeroMainActivity
import com.example.androidfundamentals.home.SharedViewModel
import com.example.androidfundamentals.home.fight.FightFragment

class HeroListFragment(): Fragment(), HeroClicked {

    private lateinit var binding: HeroListFragmentBinding // UI
    private val viewModel: SharedViewModel by viewModels() // 1st VM, ensure HeroListVM is typed as viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroListFragmentBinding.inflate(inflater)
        Log.w("Tag", "HeroListFrag > onCreateView...")
        viewModel.fetchHeroes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Remove use of sample data below
//        val listOfHeroesSample = listOfHeroesSample
//        val adapter = HeroCellAdapter(listOfHeroesSample, this) // GTG, instantiate adapter, send List<Hero>

        // check hero data right before use in adapter
        Log.w("Tag", "HeroListFrag > onViewCreated > heroesFight = ${viewModel.heroesFight}") // empty

        val adapter = HeroCellAdapter(viewModel.heroesFight, this) // instantiate adapter, send List<Hero>

        binding.rvListOfHeroes.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvListOfHeroes.adapter = adapter

    } // load data in adapters


    override fun heroSelectionClicked(hero: Hero) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fFragment, FightFragment(hero))
            .addToBackStack(HeroMainActivity::javaClass.name)
            .commit()
    } // send clicked hero to battle simulation
}