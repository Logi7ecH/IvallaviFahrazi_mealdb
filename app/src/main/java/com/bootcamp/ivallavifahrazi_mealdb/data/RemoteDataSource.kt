package com.bootcamp.ivallavifahrazi_mealdb.data

import android.util.Log
import com.bootcamp.ivallavifahrazi_mealdb.data.network.api.MealsApi
import com.bootcamp.ivallavifahrazi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseDetailMeals
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseMeals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val mealsApi:MealsApi) {
    suspend fun getMenu():Flow<NetworkResult<ResponseMeals>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val meals = mealsApi.getMenu()

            if(meals.isSuccessful){
                val responseBody = meals.body()

                if(responseBody?.meals?.isEmpty() == true){
                    emit(NetworkResult.Error("Meals List not Found"))
                }else{
                    emit(NetworkResult.Success(responseBody!!))
                }
            }else{
                // request data failed
                Log.d("apiServiceError", "statusCode:${meals.code()}, message:${meals.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }

        }catch (e:Exception){
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMenuDetail(menuId: String?):Flow<NetworkResult<ResponseDetailMeals>> = flow {
        try{
            emit(NetworkResult.Loading(true))
            val mealDetail = mealsApi.getMenuDetail(menuId)

            if(mealDetail.isSuccessful){
                val responseBody = mealDetail.body()
                if(responseBody?.meals?.isEmpty() == true){
                    emit(NetworkResult.Error("Meals List not Found"))
                }else{
                    emit(NetworkResult.Success(responseBody!!))
                }
            }else{
                // request data failed
                Log.d("apiServiceError", "statusCode:${mealDetail.code()}, message:${mealDetail.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}