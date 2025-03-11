package com.noemi.aichef.room

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Ingredients(
    val ingredients: List<String> = emptyList()
) : Parcelable