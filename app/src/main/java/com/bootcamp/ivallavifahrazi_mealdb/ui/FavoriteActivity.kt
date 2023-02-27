package com.bootcamp.ivallavifahrazi_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bootcamp.ivallavifahrazi_mealdb.adapter.FavoriteMealsAdapter
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import com.bootcamp.ivallavifahrazi_mealdb.databinding.ActivityFavoriteBinding
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.DetailViewModel
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val favoriteGameAdapter by lazy { FavoriteMealsAdapter(favoriteViewModel) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"
        favoriteViewModel.favoriteMealList.observe(this) {result ->
            Log.d("cek", result.toString())
            if(result.isEmpty()){
                binding.apply {
                    rvFavoriteGame.isVisible = false
                    emptyTv.isVisible = true
                }
            }else{
                binding.rvFavoriteGame.apply {
                    adapter = favoriteGameAdapter
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@FavoriteActivity)
                }
                favoriteGameAdapter.apply {
                    setData(result)
                    setOnItemCallback(object : FavoriteMealsAdapter.IOnFavoriteItemCallBack{
                        override fun onFavoriteItemClickCallback(data: MealEntity) {
                            val intent =Intent(this@FavoriteActivity, DetailFavoriteActivity::class.java)
                            intent.putExtra(DetailFavoriteActivity.EXTRA_DETAIL, data)
                            startActivity(intent)
                        }

                    })
                }
            }
            return@observe
        }
    }

}