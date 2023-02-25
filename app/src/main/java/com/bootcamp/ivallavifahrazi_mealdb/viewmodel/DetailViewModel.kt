package com.bootcamp.ivallavifahrazi_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.bootcamp.ivallavifahrazi_mealdb.data.LocalDataSource
import com.bootcamp.ivallavifahrazi_mealdb.data.RemoteDataSource
import com.bootcamp.ivallavifahrazi_mealdb.data.Repository
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealDatabase
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import com.bootcamp.ivallavifahrazi_mealdb.data.network.Service
import com.bootcamp.ivallavifahrazi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseDetailMeals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val remoteService = Service.mealsService
    private val remote = RemoteDataSource(remoteService)

    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)
    private val repository = Repository(remote, local)

    private val _mealsDetail: MutableLiveData<NetworkResult<ResponseDetailMeals>> = MutableLiveData()
    val mealsDetail: LiveData<NetworkResult<ResponseDetailMeals>> = _mealsDetail

    fun fetchMealsDetail(idMeal: String?){
        viewModelScope.launch {
            repository.remote!!.getMenuDetail(idMeal).collect() {result ->
                _mealsDetail.value = result
            }
        }
    }

    val favoriteMealList:LiveData<List<MealEntity>> = repository.local!!.listMeals().asLiveData()
    fun insertFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.local!!.insertMeal(mealEntity)
        }
    }
    fun deleteFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}