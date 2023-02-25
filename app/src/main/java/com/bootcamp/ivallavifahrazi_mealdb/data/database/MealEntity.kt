package com.bootcamp.ivallavifahrazi_mealdb.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bootcamp.ivallavifahrazi_mealdb.model.DetailMealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.MealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseDetailMeals
import com.bootcamp.ivallavifahrazi_mealdb.utils.Constant.MEAL_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = MEAL_TABLE_NAME)
@Parcelize
data class MealEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val meal:ResponseDetailMeals
    ):Parcelable