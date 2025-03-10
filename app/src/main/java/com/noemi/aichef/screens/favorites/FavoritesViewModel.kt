package com.noemi.aichef.screens.favorites

import androidx.lifecycle.viewModelScope
import com.noemi.aichef.base.BaseViewModel
import com.noemi.aichef.model.FavoritesUIEvent
import com.noemi.aichef.repository.RecipeRepository
import com.noemi.aichef.room.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel<FavoritesUIEvent>() {

    private val _favoritesState = MutableStateFlow(FavoritesRecipesState())
    val favoritesState = _favoritesState.asStateFlow()

    init {
        onEvent(FavoritesUIEvent.Display)
    }

    override fun handleEvent(event: FavoritesUIEvent) {
        when (event) {
            is FavoritesUIEvent.Display -> loadFavoritesRecipes()
            is FavoritesUIEvent.Delete -> deleteRecipe(event.recipe)
        }
    }

    private fun loadFavoritesRecipes() {
        viewModelScope.launch {
            _favoritesState.update {
                it.copy(isLoading = true)
            }

            delay(900)

            recipeRepository.observeRecipes()
                .catch { error ->
                    _favoritesState.update {
                        it.copy(
                            isLoading = false,
                            hasError = error.message ?: "Something went wrong, try it again!"
                        )
                    }

                    delay(900)

                    _favoritesState.update {
                        it.copy(
                            hasError = ""
                        )
                    }
                }
                .collectLatest { recipes ->
                    _favoritesState.update {
                        it.copy(
                            isLoading = false,
                            hasError = "",
                            recipes = recipes
                        )
                    }
                }
        }
    }

    private fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.removeRecipe(recipe)
        }
    }

    data class FavoritesRecipesState(
        val isLoading: Boolean = false,
        val hasError: String = "",
        val recipes: List<Recipe> = emptyList()
    )
}