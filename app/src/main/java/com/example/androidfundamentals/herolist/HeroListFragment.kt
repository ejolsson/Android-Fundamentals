package com.example.androidfundamentals.herolist

import com.example.androidfundamentals.fight.listOfHeroesSample
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidfundamentals.databinding.HeroListFragmentBinding

class HeroListFragment(val callback: HeroClicked): Fragment(), HeroClicked {

    private lateinit var binding: HeroListFragmentBinding // UI
    val heroListViewModel: HeroListViewModel by viewModels() // 1st VM, ensure HeroListVM is typed as viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroListFragmentBinding.inflate(inflater)
//        setContentView(binding.root) // attempting to create a "context"

        // 4 lines below brought in from HeroActivityMain
        val listOfHeroesSample = listOfHeroesSample // sample data TODO: Remove
        val adapter = HeroCellAdapter(listOfHeroesSample, this) // instantiate adapter.

        // TODO: Add "context!"
//        binding.rvListOfHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvListOfHeroes.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener {
            // Callback methods, like fetchHeroes. Should this go under viewModel?
            // callback.onClick
        }
    }

//    private fun initListener() {
//        binding.
//    }
}