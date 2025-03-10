package com.noemi.aichef.navigation

import com.noemi.aichef.R

enum class ChefDestination(val title: Int, val icon: Int) {

    SUGGESTED(title = R.string.label_suggested, icon = R.drawable.ic_suggested),
    FAVORITE(title = R.string.label_favorites, icon = R.drawable.ic_favorite);

    companion object {
        fun getDestinations() = listOf(SUGGESTED, FAVORITE)
    }
}
