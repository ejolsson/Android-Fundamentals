package com.example.androidfundamentals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.login)
//        setContentView(R.layout.hero_list)
        setContentView(R.layout.hero_fight)
    }
}