package com.bootcamp.ivallavifahrazi_mealdb.api

import com.bootcamp.ivallavifahrazi_mealdb.data.network.api.MealsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val baseUrl = "https://www.themealdb.com/api/json/v1/1/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): MealsApi {
        return getRetrofit().create(MealsApi::class.java)
    }
}