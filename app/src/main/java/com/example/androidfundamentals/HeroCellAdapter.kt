package com.example.androidfundamentals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfundamentals.databinding.HeroCellBinding

class HeroCellAdapter(val listOfHeroes: List<ViewModelMainActivity.Hero>): RecyclerView.Adapter<HeroCellAdapter.MainActivityViewHolder>() {

//    lateinit var binding : HeroCellBinding // name CamelCased hero_list.xml.

    class MainActivityViewHolder(private var item: HeroCellBinding) : RecyclerView.ViewHolder(item.root) {

        fun showHero(hero: ViewModelMainActivity.Hero, par: Boolean) {
            item.heroNameCell.text = hero.name
            item.heroDescriptionCell.text = hero.description
        // TODO: add image
        // item.heroThumb...

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val binding = HeroCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainActivityViewHolder(binding)
    }

    override fun getItemCount(): Int { // set number of rows in table = hero.count??
        return listOfHeroes.size
    }

    // not sure what section below does...
    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.showHero(listOfHeroes[position], position % 2 == 0)
    }
}