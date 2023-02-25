package com.bootcamp.ivallavifahrazi_mealdb.data.network.api

import com.bootcamp.ivallavifahrazi_mealdb.model.DetailMealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseDetailMeals
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseMeals
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealsApi {
    @GET("filter.php?c=Seafood")
    suspend fun getMenu(): Response<ResponseMeals>

    @GET("lookup.php")
    suspend fun getMenuDetail(
        @Query("i") idMeal: String?
    ): Response<ResponseDetailMeals>

}
