package com.noemi.aichef.room.converters

import androidx.room.TypeConverter
import com.noemi.aichef.room.Ingredients
import kotlinx.serialization.json.Json

class IngredientsTypeConverter {

    @TypeConverter
    fun fromIngredientsToString(ingredients: Ingredients): String =
        Json.encodeToString(Ingredients.serializer(), ingredients)

    @TypeConverter
    fun fromStringToIngredients(json: String): Ingredients =
        Json.decodeFromString(Ingredients.serializer(), json)
}