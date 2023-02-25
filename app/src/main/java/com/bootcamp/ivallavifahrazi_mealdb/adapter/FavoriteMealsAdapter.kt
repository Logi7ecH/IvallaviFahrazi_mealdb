package com.bootcamp.ivallavifahrazi_mealdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bootcamp.ivallavifahrazi_mealdb.databinding.MenuRowLayoutBinding
import com.bootcamp.ivallavifahrazi_mealdb.model.DetailMealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.MealsItem
import com.bumptech.glide.Glide

class FavoriteMealsAdapter:RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteViewHolder>() {
    private var dataDetail:List<DetailMealsItem> = listOf()
    inner class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = MenuRowLayoutBinding.bind(view)

        fun bind(meal: DetailMealsItem){
            binding.apply {
                itemNameMenu.text = meal.strMeal
                Glide.with(itemImageMenu)
                    .load(meal.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .into(itemImageMenu)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_row_layout,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(dataDetail[position])
    }

    override fun getItemCount(): Int {
        return dataDetail.size
    }
    fun setData(data: List<DetailMealsItem>){
        dataDetail = data
        notifyDataSetChanged()
    }

}