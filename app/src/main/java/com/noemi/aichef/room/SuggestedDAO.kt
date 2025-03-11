package com.noemi.aichef.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noemi.aichef.util.Constants.SUGGESTED_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestedDAO {

    @Query("SELECT * FROM $SUGGESTED_TABLE")
    fun observeSuggestedRecipes(): Flow<List<SuggestedRecipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSuggestedRecipes(recipe: List<SuggestedRecipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSuggestedRecipe(recipe: SuggestedRecipe)

    @Query("DELETE FROM $SUGGESTED_TABLE")
    suspend fun deleteSuggestedRecipe()
}