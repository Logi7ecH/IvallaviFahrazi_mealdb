package com.bootcamp.ivallavifahrazi_mealdb.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.graphics.toColor
import androidx.core.view.isVisible
import com.bootcamp.ivallavifahrazi_mealdb.R
import com.bootcamp.ivallavifahrazi_mealdb.api.ApiConfig
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import com.bootcamp.ivallavifahrazi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ivallavifahrazi_mealdb.databinding.ActivityDetailMealsBinding
import com.bootcamp.ivallavifahrazi_mealdb.model.DetailMealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.MealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseDetailMeals
import com.bootcamp.ivallavifahrazi_mealdb.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMealsBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var menuDetail:ResponseDetailMeals

    companion object{
        const val EXTRA_MEALS = "extra_meals"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailMealsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val dataMeals = intent.getParcelableExtra<MealsItem>(EXTRA_MEALS)
        val idMeals = dataMeals?.idMeal.toString()

        supportActionBar?.title = "Details"

        detailViewModel.fetchMealsDetail(idMeals)
        detailViewModel.mealsDetail.observe(this){result ->
            when(result) {
                is NetworkResult.Loading ->{
                    handleUi(
                        mealWrapper = false,
                        progressbar = true,
                        errorTV = false
                    )
                }
                is NetworkResult.Error -> {
                    handleUi(
                        mealWrapper = false,
                        progressbar = false,
                        errorTV = true
                    )
                }
                is NetworkResult.Success -> {
                    val responseDetail = result.data
                    val detailMeals = responseDetail?.meals?.get(0)
                    menuDetail =result.data!!
                    binding.apply {
                        tvJudul.text = detailMeals?.strMeal
                        tvArea.text = detailMeals?.strArea
                        tvInstruction.text = detailMeals?.strInstructions

                        Glide.with(this@DetailMealsActivity)
                            .load(detailMeals?.strMealThumb)
                            .error(R.drawable.ic_launcher_background)
                            .circleCrop()
                            .into(imgMeals)
                    }

                    handleUi(
                        mealWrapper = true,
                        progressbar = false,
                        errorTV = false
                    )
                }
            }
        }

        isFavoriteMeals(dataMeals!!)
    }


    private fun isFavoriteMeals(mealSelected : MealsItem){
        detailViewModel.favoriteMealList.observe(this){result ->
            val menu = result.find { fav ->
                fav.meal.meals!![0]?.idMeal == mealSelected.idMeal
            }
            if(menu != null){
                binding.btnLove.apply {
                    setButtonDrawable(R.drawable.ic_baseline_favorite_24)
                    setOnClickListener {
                        deleteFavoriteMeals(menu.id)
                        val snackbar = Snackbar.make(binding.myCoordinatorLayout, "Success Remove from Favorite", Snackbar.LENGTH_SHORT)
                        snackbar.setAction("Undo") {
                            insertFavoriteMeals()
                        }
                        snackbar.show()
                    }
                }
            }else{
                binding.btnLove.apply {
                    setButtonDrawable(R.drawable.ic_notfavorite)
                    setOnClickListener {
                        insertFavoriteMeals()
                        val snackbar = Snackbar.make(binding.myCoordinatorLayout, "Success Adding to Favorite", Snackbar.LENGTH_SHORT)
                        snackbar.show()
                    }
                }
            }
            return@observe
        }
    }
    private fun deleteFavoriteMeals(mealEntityId: Int){
        val mealEntity = MealEntity(id = mealEntityId, meal = menuDetail)
        detailViewModel.deleteFavoriteMeal(mealEntity)

    }

    private fun insertFavoriteMeals(){
        val mealEntity =MealEntity(meal = menuDetail)
        detailViewModel.insertFavoriteMeal(mealEntity)

    }



    private fun handleUi(
        mealWrapper: Boolean,
        progressbar: Boolean,
        errorTV: Boolean
    ){
        binding.apply {
            menuWrapper.isVisible = mealWrapper
            progressBar.isVisible = progressbar
            errorText.isVisible = errorTV
        }
    }
}

