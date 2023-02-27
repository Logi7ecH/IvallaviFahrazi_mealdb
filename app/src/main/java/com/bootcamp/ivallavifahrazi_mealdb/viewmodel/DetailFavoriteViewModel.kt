package com.bootcamp.ivallavifahrazi_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.ivallavifahrazi_mealdb.data.LocalDataSource
import com.bootcamp.ivallavifahrazi_mealdb.data.RemoteDataSource
import com.bootcamp.ivallavifahrazi_mealdb.data.Repository
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealDatabase
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import com.bootcamp.ivallavifahrazi_mealdb.data.network.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailFavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val remoteService = Service.mealsService
    private val remote = RemoteDataSource(remoteService)

    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)
    private val repository = Repository(remote, local)

    fun deleteFavoriteMeals(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}