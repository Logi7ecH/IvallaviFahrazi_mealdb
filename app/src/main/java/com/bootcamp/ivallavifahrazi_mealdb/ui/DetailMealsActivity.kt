package com.bootcamp.ivallavifahrazi_mealdb.ui

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMealsBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var menuDetail:DetailMealsItem

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
                    //Cek apakah udah fav atau blm, jika blm maka kode dibawah
//                    binding.btnLove.setButtonDrawable(R.drawable.ic_notfavorite)
                    //Cek apakah udah fav atau blm, jika suda maka kode dibawah
                    //binding.setbtnlove(Favorit)
//
//                    binding.btnLove.setOnClickListener{
//                        if(binding.btnLove.isChecked){
//                            binding.btnLove.setButtonDrawable(R.drawable.ic_baseline_favorite_24)
//                        } else {
//                            binding.btnLove.setButtonDrawable(R.drawable.ic_notfavorite)
//                        }
//                    }
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
                fav.meal.idMeal == mealSelected.idMeal
            }
            if(menu != null){
                binding.btnLove.apply {
                    setButtonDrawable(R.drawable.ic_baseline_favorite_24)
                    setOnClickListener {
                        deleteFavoriteMeals(menu.id)
                    }
                }
            }else{
                binding.btnLove.apply {
                    setButtonDrawable(R.drawable.ic_notfavorite)
                    setOnClickListener {
                        insertFavoriteMeals()
                    }
                }
            }
        }
    }
    private fun deleteFavoriteMeals(mealEntityId: Int){
        val mealEntity = MealEntity(id = mealEntityId, meal = menuDetail)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove to favorite", Toast.LENGTH_SHORT).show()
    }

    private fun insertFavoriteMeals(){
        val mealEntity =MealEntity(meal = menuDetail)
        detailViewModel.insertFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully added to favorite", Toast.LENGTH_SHORT).show()
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

