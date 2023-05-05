package com.example.androidfundamentals.home

import android.content.Context
import android.content.Intent
import com.example.androidfundamentals.home.herolist.HeroListFragment
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfundamentals.databinding.HeroActivityBinding
import com.example.androidfundamentals.login.tokenPublic

class HeroActivity : AppCompatActivity() {

    companion object {

        fun launch(context: Context, token: String) {
            val intent = Intent(context, HeroActivity::class.java)
            intent.putExtra(token, tokenPublic)
            Log.w("Tag","companion token = $token")
            context.startActivity(intent)
        }
    }

    private lateinit var heroActivityBinding: HeroActivityBinding // 1st xml UI setup

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.w("Tag", "HeroAct > onCreate...")
        super.onCreate(savedInstanceState)
        heroActivityBinding = HeroActivityBinding.inflate(layoutInflater)
        setContentView(heroActivityBinding.root) // initialize UI
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityBinding.fFragment.id, HeroListFragment())
            .commitNow()
    sharedViewModel.fetchHeroes() // works in log
    }
}