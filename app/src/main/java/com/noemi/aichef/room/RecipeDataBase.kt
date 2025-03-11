package com.noemi.aichef.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noemi.aichef.room.converters.IngredientsTypeConverter
import com.noemi.aichef.room.converters.StepsTypeConverter

@Database(entities = [Recipe::class, SuggestedRecipe::class], version = 2)
@TypeConverters(
    IngredientsTypeConverter::class,
    StepsTypeConverter::class
)
abstract class RecipeDataBase : RoomDatabase() {

    abstract fun provideRecipeDao(): RecipeDAO

    abstract fun provideSuggestedDao(): SuggestedDAO
}