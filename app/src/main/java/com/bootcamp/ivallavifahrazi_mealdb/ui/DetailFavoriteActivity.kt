package com.bootcamp.ivallavifahrazi_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import com.bootcamp.ivallavifahrazi_mealdb.databinding.ActivityDetailFavoriteBinding
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.DetailFavoriteViewModel

class DetailFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFavoriteBinding
    private val detailViewModel by viewModels<DetailFavoriteViewModel>()
    private lateinit var menuDetail: MealEntity
    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Favorite"

        val detailFavoriteMeals =intent.getParcelableExtra<MealEntity>(EXTRA_DETAIL)
        binding.mealDetail =detailFavoriteMeals?.meal

        binding.btnDelete.setOnClickListener {
            deleteFavoriteMeals(detailFavoriteMeals!!)
            val intent = Intent(this, FavoriteActivity::class.java)

            startActivity(intent)
            finish()
        }
    }

    private fun deleteFavoriteMeals(mealEntity: MealEntity) {
        detailViewModel.deleteFavoriteMeals(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()

    }
}