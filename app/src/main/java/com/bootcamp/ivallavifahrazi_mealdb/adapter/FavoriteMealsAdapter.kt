package com.bootcamp.ivallavifahrazi_mealdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import com.bootcamp.ivallavifahrazi_mealdb.databinding.MenuRowLayoutBinding
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.DetailViewModel
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.FavoriteViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class FavoriteMealsAdapter(private val Meals : FavoriteViewModel):RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<MealEntity>(){
        override fun areItemsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
            return oldItem == newItem
        }
    }

    private val differ =AsyncListDiffer(this, diffCallBack)

    inner class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = MenuRowLayoutBinding.bind(view)

        fun bind(meal: MealEntity){
            binding.apply {
                itemNameMenu.text = meal.meal.meals!![0]?.strMeal
                Glide.with(itemImageMenu)
                    .load(meal.meal.meals!![0]?.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .into(itemImageMenu)
            }
            binding.btnDelete.setOnClickListener {
                Meals.deleteFavoriteMeals(meal)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_row_layout,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setData(data: List<MealEntity>){
        differ.submitList(data)
    }

}


