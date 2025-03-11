package com.noemi.aichef.room

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Steps(
    val steps: List<String> = emptyList()
) : Parcelable