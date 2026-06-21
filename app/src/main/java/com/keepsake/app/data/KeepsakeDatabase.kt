package com.keepsake.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class KeepsakeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile private var INSTANCE: KeepsakeDatabase? = null

        fun getInstance(context: Context): KeepsakeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    KeepsakeDatabase::class.java,
                    "keepsake.db"
                ).build().also { INSTANCE = it }
            }
    }
}
