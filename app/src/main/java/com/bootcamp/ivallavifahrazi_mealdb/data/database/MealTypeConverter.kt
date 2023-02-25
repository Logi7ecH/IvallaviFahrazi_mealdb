package com.bootcamp.ivallavifahrazi_mealdb.data.database

import androidx.room.TypeConverter
import com.bootcamp.ivallavifahrazi_mealdb.model.DetailMealsItem
import com.bootcamp.ivallavifahrazi_mealdb.model.ResponseDetailMeals
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealDataToString(meal:ResponseDetailMeals): String{
        return gson.toJson(meal)
    }

    @TypeConverter
    fun gameStringToData(string: String): ResponseDetailMeals{
        val listType = object: TypeToken<ResponseDetailMeals>() {}.type
        return gson.fromJson(string, listType)
    }
}