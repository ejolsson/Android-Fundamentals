package com.example.androidfundamentals.home

import com.example.androidfundamentals.home.herolist.HeroListFragment
import com.example.androidfundamentals.home.herolist.HeroClicked
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfundamentals.databinding.HeroActivityMainBinding

class HeroMainActivity : AppCompatActivity() {

    private lateinit var heroActivityMainBinding: HeroActivityMainBinding // 1st xml UI setup

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        heroActivityMainBinding = HeroActivityMainBinding.inflate(layoutInflater)
        setContentView(heroActivityMainBinding.root) // initialize UI
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityMainBinding.fFragment.id, HeroListFragment())
            .commitNow()
    }
}