package com.bootcamp.ivallavifahrazi_mealdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bootcamp.ivallavifahrazi_mealdb.adapter.MealsAdapter
import com.bootcamp.ivallavifahrazi_mealdb.api.ApiConfig
import com.bootcamp.ivallavifahrazi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ivallavifahrazi_mealdb.databinding.ActivityMainBinding
import com.bootcamp.ivallavifahrazi_mealdb.model.MealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseMeals
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Ivallavi Fahrazi"

        mainViewModel.mealsList.observe(this@MainActivity){res ->
            when(res){
                is NetworkResult.Loading ->{
                    handleUi(
                        recyclerView = false,
                        progressbar = true,
                        errorTV = false
                    )
                }
                is NetworkResult.Error -> {
                    binding.errorText.text = res.errorMessage
                    handleUi(
                        recyclerView = false,
                        progressbar = false,
                        errorTV = true
                    )
                }
                is NetworkResult.Success -> {
                    val mealsAdapter = MealsAdapter()
                    mealsAdapter.setData(res.data?.meals as List<MealsItem>)
                    binding.rvMenu.apply {
                        layoutManager = GridLayoutManager(this@MainActivity, 2)
                        setHasFixedSize(true)
                        adapter = mealsAdapter
                    }
                    binding.recipeSize.text = res.data.meals.size.toString() + " Recipes found"

                    handleUi(
                        recyclerView = true,
                        progressbar = false,
                        errorTV = false
                    )
                }
            }
        }


    }

    private fun handleUi(
        recyclerView: Boolean,
        progressbar: Boolean,
        errorTV: Boolean
    ){
        binding.apply {
            rvMenu.isVisible = recyclerView
            progressBar.isVisible = progressbar
            errorText.isVisible = errorTV
        }
    }
}