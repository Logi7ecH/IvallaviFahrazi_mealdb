package com.bootcamp.ivallavifahrazi_mealdb.data.database

import android.content.Context
import androidx.room.*

@Database(
    entities =[MealEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDAO

    companion object{
        @Volatile
        private var instance: MealDatabase? = null

        fun getDatabase(ctx: Context): MealDatabase{
            val tempInstance = instance
            if(tempInstance != null){
                instance = tempInstance
            }

            synchronized(this   ){
                val newInstance = Room.databaseBuilder(
                    ctx.applicationContext, MealDatabase::class.java,
                    "meals_database"
                ).build()

                instance = newInstance
                return newInstance
            }
        }

    }
}