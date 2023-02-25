package com.bootcamp.ivallavifahrazi_mealdb.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsEntity: MealEntity)

    @Query("SELECT * FROM meal_table ORDER BY id ASC")
    fun listMeals(): Flow<List<MealEntity>>

    @Delete()
    suspend fun deleteMeal(mealsEntity: MealEntity)

    @Query("DELETE FROM meal_table")
    suspend fun deleteAllMeals()
}