package com.example.androidfundamentals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import com.example.androidfundamentals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var counter = 0
    private var tagInt = "MyInteger"
    private var tagString = "MyString"

    private lateinit var binding : ActivityMainBinding // all projects?

    private val viewModel : ViewModelMainActivity by viewModels() // L5, 0.35.00, extra library needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.login)
//        setContentView(R.layout.hero_list)
//        setContentView(R.layout.hero_fight)
        setContentView(binding.root)
        Log.w("Tag", "onCreate")
        savedInstanceState?.let {
            counter = it.getInt(tagInt, 0)
            Log.w("Tag",it.getString(tagString).toString())
        }
        counter++
        Log.w("Tag", "counter = $counter")
    }

    override fun onStart() {
        super.onStart()
        Log.w("Tag", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w("Tag", "onResume")
    }

    override fun onPause() {
        Log.w("Tag", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.w("Tag", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.w("Tag", "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("Tag", "onRestart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.w("Tag", "onSaveInstanceState")
        outState.putInt(tagInt, counter)
        outState.putString(tagString, "El contador es $counter")

    }

    // TODO: - Pull image from internet -
    // https://www.tutorialspoint.com/how-do-i-load-an-imageview-by-url-on-android
}