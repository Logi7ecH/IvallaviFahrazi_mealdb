package com.bootcamp.ivallavifahrazi_mealdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bootcamp.ivallavifahrazi_mealdb.adapter.FavoriteMealsAdapter
import com.bootcamp.ivallavifahrazi_mealdb.databinding.ActivityFavoriteBinding
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val favoriteGameAdapter by lazy { FavoriteMealsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}