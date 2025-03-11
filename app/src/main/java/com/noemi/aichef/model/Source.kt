package com.noemi.aichef.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val source: String = ""
) : Parcelable

enum class SourceType {
    SUGGESTED,
    FAVORITE;
}