package com.noemi.aichef.model

enum class RecipeNumber {
    THREE,
    FOUR,
    FIVE;

    companion object {
        fun RecipeNumber.toName(): String = this.name.lowercase()
    }
}
