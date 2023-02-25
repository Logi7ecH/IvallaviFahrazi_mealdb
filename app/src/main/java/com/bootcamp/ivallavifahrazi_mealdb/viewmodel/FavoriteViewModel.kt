package com.bootcamp.ivallavifahrazi_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bootcamp.ivallavifahrazi_mealdb.data.LocalDataSource
import com.bootcamp.ivallavifahrazi_mealdb.data.Repository
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealDatabase
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseMeals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application):AndroidViewModel(application) {
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataSource(mealDao)
    private val repository = Repository(local = local)

    val favoriteMealList: LiveData<List<MealEntity>> = repository.local!!.listMeals().asLiveData()

    fun deleteFavoriteMeals(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.local!!.deleteMeal(mealEntity)
        }
    }
    fun insertFavoriteMeals(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.local!!.insertMeal(mealEntity)
        }
    }
}