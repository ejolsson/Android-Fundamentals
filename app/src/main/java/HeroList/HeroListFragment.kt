package HeroList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidfundamentals.databinding.HeroListFragmentBinding

interface HeroClicked {
    fun heroSelectionClicked(hero: Fight.Hero) {
        // Todo: navigate to FightFragment & layout
    }
}// Show scrollable list of heroes (name & picture)
class HeroListFragment(val callback: HeroClicked): Fragment() {

    private lateinit var binding: HeroListFragmentBinding // UI
    val heroListViewModel: HeroListViewModel by viewModels() // 1st VM, ensure HeroListVM is typed as viewModel()
    // no need for any other viewModels, I think

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HeroListFragmentBinding.inflate(inflater)
        return binding.root
    }

//    Picasso.get().load("https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300").into(cellBinding.heroThumb)

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