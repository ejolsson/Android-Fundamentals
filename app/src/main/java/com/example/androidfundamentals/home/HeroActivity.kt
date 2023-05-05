package com.example.androidfundamentals.home

import android.content.Context
import android.content.Intent
import com.example.androidfundamentals.home.herolist.HeroListFragment
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfundamentals.databinding.HeroActivityBinding

class HeroActivity : AppCompatActivity() {

    companion object {

        const val TAG_TOKEN = "TAG_TOKEN"
        fun launch(context: Context, token: String) {
            val intent = Intent(context, HeroActivity::class.java)
            intent.putExtra(TAG_TOKEN, token)
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
        intent.extras?.getString(TAG_TOKEN, "")?.let { token ->
            sharedViewModel.fetchHeroes(token) // works in log
        }
    }
}