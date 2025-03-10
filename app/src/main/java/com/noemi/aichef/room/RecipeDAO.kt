package com.noemi.aichef.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDAO {

    @Query("SELECT * FROM AI_CHEF_TABLE")
    fun observeRecipes(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}