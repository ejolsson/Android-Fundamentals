package com.example.androidfundamentals.home.herolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfundamentals.R
import com.example.androidfundamentals.data.Hero
import com.example.androidfundamentals.data.listOfHeroesSample
import com.example.androidfundamentals.databinding.HeroListFragmentBinding
import com.example.androidfundamentals.home.HeroMainActivity
import com.example.androidfundamentals.home.SharedViewModel
import com.example.androidfundamentals.home.fight.FightFragment

class HeroListFragment(): Fragment(), HeroClicked {

    private lateinit var binding: HeroListFragmentBinding // UI
    val viewModel: SharedViewModel by viewModels() // 1st VM, ensure HeroListVM is typed as viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfHeroesSample = listOfHeroesSample // sample data TODO: Remove
        val adapter = HeroCellAdapter(listOfHeroesSample, this) // instantiate adapter.

        binding.rvListOfHeroes.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvListOfHeroes.adapter = adapter

//        binding.root.setOnClickListener {
//            // Callback methods, like fetchHeroes. Should this go under viewModel?
//            // callback.onClick
//        }
    }

    override fun heroSelectionClicked(hero: Hero) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fFragment, FightFragment(hero))
            .addToBackStack(HeroMainActivity::javaClass.name)
            .commit()
    }
}