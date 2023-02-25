package com.bootcamp.ivallavifahrazi_mealdb.data

import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealDAO
import com.bootcamp.ivallavifahrazi_mealdb.data.database.MealEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao:MealDAO) {
    suspend fun insertMeal(mealEntity: MealEntity) = dao.insertMeal(mealEntity)
    fun listMeals(): Flow<List<MealEntity>> = dao.listMeals()
    suspend fun deleteMeal(mealsEntity: MealEntity) = dao.deleteMeal(mealsEntity)
    suspend fun deleteAllMeals() = dao.deleteAllMeals()
}