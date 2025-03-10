package com.noemi.aichef.room.converters

import androidx.room.TypeConverter
import com.noemi.aichef.room.Steps
import kotlinx.serialization.json.Json

class StepsTypeConverter {

    @TypeConverter
    fun fromStringToSteps(json: String): Steps =
        Json.decodeFromString(Steps.serializer(), json)

    @TypeConverter
    fun fromStepsToString(steps: Steps): String =
        Json.encodeToString(Steps.serializer(), steps)
}