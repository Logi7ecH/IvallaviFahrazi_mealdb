package com.bootcamp.ivallavifahrazi_mealdb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.ivallavifahrazi_mealdb.ui.DetailMealsActivity
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bootcamp.ivallavifahrazi_mealdb.databinding.ItemRowMealsBinding
import com.bootcamp.ivallavifahrazi_mealdb.model.DetailMealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.MealsItem
import com.bumptech.glide.Glide


class MealsAdapter(): RecyclerView.Adapter<MealsAdapter.MyViewHolder>(){
    private var dataMenu:List<MealsItem> = listOf()
    private var detailDataMenu:List<DetailMealsItem> = listOf()
    inner class MyViewHolder(view:View): RecyclerView.ViewHolder(view){
        private val binding = ItemRowMealsBinding.bind(view)

        fun bind(meals: MealsItem){
            binding.apply {
                itemNameMenu.text = meals.strMeal

                Glide.with(itemImageMenu)
                    .load(meals.strMealThumb)
                    .error(R.drawable.ic_launcher_background)
                    .circleCrop()
                    .into(itemImageMenu)

                binding.cardMeals.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMealsActivity::class.java)
                    intent.putExtra(DetailMealsActivity.EXTRA_MEALS, dataMenu[adapterPosition])
                    itemView.context.startActivity(intent)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_row_meals,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataMenu[position])
    }

    override fun getItemCount(): Int {
        return dataMenu.size
    }

    fun setData(data: List<MealsItem>){
        dataMenu = data
        notifyDataSetChanged()
    }
}