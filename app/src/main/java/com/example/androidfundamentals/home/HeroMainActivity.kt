package com.example.androidfundamentals.home

import android.content.Context
import android.content.Intent
import com.example.androidfundamentals.home.herolist.HeroListFragment
import com.example.androidfundamentals.home.herolist.HeroClicked
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidfundamentals.databinding.HeroActivityMainBinding

class HeroMainActivity : AppCompatActivity() {

    companion object {

        fun launch(context: Context, token: String) {
            val intent = Intent(context, HeroMainActivity::class.java)
            intent.putExtra(token, token)
            Log.w("Tag","companion token = $token")
            context.startActivity(intent)
        }
    }

    private lateinit var heroActivityMainBinding: HeroActivityMainBinding // 1st xml UI setup

    private val sharedViewModel: SharedViewModel by viewModels()

//    init { // app crash with this
//        sharedViewModel.getHeroes()
//    }
//
//    fun relaunch() {
//        sharedViewModel.getHeroes()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        heroActivityMainBinding = HeroActivityMainBinding.inflate(layoutInflater)
        setContentView(heroActivityMainBinding.root) // initialize UI
        supportFragmentManager
            .beginTransaction()
            .replace(heroActivityMainBinding.fFragment.id, HeroListFragment())
            .commitNow()
    Log.w("Tag", "HeroAct > onCreate...")
//    sharedViewModel.getHeroes() // not showing
    }
}