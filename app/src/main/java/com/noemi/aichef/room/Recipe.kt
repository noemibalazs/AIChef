package com.noemi.aichef.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noemi.aichef.util.Constants.CHEF_TABLE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(CHEF_TABLE)
data class Recipe(

    @PrimaryKey
    val name: String = "",
    val description: String = "",
    val ingredients: Ingredients = Ingredients(),
    val steps: Steps = Steps(),
    var isFavorite: Boolean = false,
    var index: Int = 0
) : Parcelable

@Parcelize
@Serializable
data class Ingredients(
    val ingredients: List<String> = emptyList()
) : Parcelable

@Parcelize
@Serializable
data class Steps(
    val steps: List<String> = emptyList()
) : Parcelable
