package HeroList

import Login.LoginViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import Fight.Hero
import com.example.androidfundamentals.databinding.HeroCellBinding
import com.squareup.picasso.Picasso

interface HeroClicked {
    fun heroSelectionClicked(hero: Hero) {
        // Todo: navigate to FightFragment & layout
    }
}// Show scrollable list of heroes (name & picture)

class HeroCellAdapter(
    private val listHeroes: List<Fight.Hero>,
    private val callback: HeroClicked,
    ): RecyclerView.Adapter<HeroCellAdapter.MainActivityViewHolder>() {

    class MainActivityViewHolder(private var item: HeroCellBinding, private val callback: HeroClicked): RecyclerView.ViewHolder(item.root) {
        fun showHero(hero: Hero, par: Boolean) {
            item.tvHeroNameCell.text = hero.name // specific hero name
//            Picasso.get().load("https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300").into(item.ivHeroThumb)
            item.lLHeroCell.setOnClickListener {// when the cell is clicked...
                callback.heroSelectionClicked(hero)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val binding = HeroCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainActivityViewHolder(binding, callback)
    }
    override fun getItemCount(): Int {
        return listHeroes.size
    }

    // not sure what section below does...
    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.showHero(listHeroes[position], position % 2 == 0)
    }
}