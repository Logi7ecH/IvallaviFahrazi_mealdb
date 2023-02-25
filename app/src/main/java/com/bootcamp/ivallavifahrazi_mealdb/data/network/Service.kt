package com.bootcamp.ivallavifahrazi_mealdb.data.network

import com.bootcamp.ivallavifahrazi_mealdb.api.ApiConfig
import com.bootcamp.ivallavifahrazi_mealdb.data.network.api.MealsApi
import com.bootcamp.ivallavifahrazi_mealdb.utils.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Service {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val mealsService:MealsApi by lazy {
        retrofit.create(MealsApi::class.java)
    }
}