package com.bootcamp.ivallavifahrazi_mealdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.ivallavifahrazi_mealdb.data.LocalDataSource
import com.bootcamp.ivallavifahrazi_mealdb.data.RemoteDataSource
import com.bootcamp.ivallavifahrazi_mealdb.data.Repository
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealDatabase
import com.bootcamp.ivallavifahrazi_mealdb.data.network.Service
import com.bootcamp.ivallavifahrazi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseMeals
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {

    private val remoteService = Service.mealsService
    private val remote = RemoteDataSource(remoteService)
    private val repository = Repository(remote)

    private var _mealsList: MutableLiveData<NetworkResult<ResponseMeals>> = MutableLiveData()
    val mealsList: LiveData<NetworkResult<ResponseMeals>> =_mealsList

    init {
        fetchMealsList()
    }

    private  fun fetchMealsList(){
        viewModelScope.launch {
            repository.remote?.getMenu()?.collect{res ->
                _mealsList.value = res
            }
        }
    }

}