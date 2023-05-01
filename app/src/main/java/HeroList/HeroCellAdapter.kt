package HeroList

import Login.LoginViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import Fight.Hero
import com.example.androidfundamentals.databinding.HeroCell2Binding
import com.squareup.picasso.Picasso

interface HeroCLicked {
    fun heroClicked(hero: Fight.Hero)
}

class HeroCellAdapter(
    private val listHeroes: List<Fight.Hero>,
    private val callback: HeroClicked,
    ): RecyclerView.Adapter<HeroCellAdapter.MainActivityViewHolder>() {

    class MainActivityViewHolder(
        private var item: HeroCell2Binding,
        private val callback: HeroClicked):
        RecyclerView.ViewHolder(item.root) {

        fun showHero(hero: Hero, par: Boolean) {
            item.tvHeroNameCell.text = hero.name
            Picasso.get().load("https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300").into(item.ivHeroThumb)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val binding = HeroCell2Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainActivityViewHolder(binding, callback)
    }

    override fun getItemCount(): Int { // set number of rows in table = hero.count??
        return listHeroes.size
    }

    // not sure what section below does...
    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.showHero(listHeroes[position], position % 2 == 0)
    }
}