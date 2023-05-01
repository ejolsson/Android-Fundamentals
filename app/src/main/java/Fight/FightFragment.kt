package Fight

import HeroList.HeroClicked
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.lifecycle.lifecycleScope
import com.example.androidfundamentals.databinding.HeroFightFragmentBinding
import com.example.androidfundamentals.databinding.HeroListFragmentBinding
import kotlinx.coroutines.launch

class FightFragment(val callback: HeroClicked): Fragment() {

    private lateinit var binding: HeroFightFragmentBinding
//    val fightViewModel: FightViewModel by viewModels()

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = HeroFightBinding.inflate(inflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.root.setOnClickListener {
//            // Callback methods, like fetchHeroes. Should this go under viewModel?
//            // callback.onClick
//        }
//    }

//    private fun initListener() {
//        binding.takeDamageButton.setOnClickListener() {
//            viewModel.takeDamage()
//        }
//    }
//
//    private fun initCollects() {
//        lifecycleScope.launch {
//            viewModel.uiState.collect{
//                Log.w("Tag", "Life updated")
//                when(it) { // must list our all 3 UiResponse cases
//                    is FightViewModel.UiResponse.Started -> binding.lifeBarLabel.text = "100%"
//                    is FightViewModel.UiResponse.LifeRemaining -> binding.lifeBarLabel.text = it.life.toString()
//                }
//            }
//        }
//    }
}