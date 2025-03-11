package com.noemi.aichef.screens.details

import androidx.lifecycle.viewModelScope
import com.noemi.aichef.base.BaseViewModel
import com.noemi.aichef.model.RecipeDetailsUIEvent
import com.noemi.aichef.model.Source
import com.noemi.aichef.model.SourceType
import com.noemi.aichef.repository.RecipeRepository
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.util.toSuggestedRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : BaseViewModel<RecipeDetailsUIEvent>() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _recipe = MutableStateFlow(Recipe())
    val recipe = _recipe.asStateFlow()

    override fun handleEvent(event: RecipeDetailsUIEvent) {
        when (event) {
            is RecipeDetailsUIEvent.StateChanged -> recipeStateChanged(event.recipe, event.source)
        }
    }

    fun onRecipeReceived(recipe: Recipe) {
        viewModelScope.launch {
            _recipe.emit(recipe)
        }
    }

    private fun recipeStateChanged(recipe: Recipe, source: Source) {
        viewModelScope.launch {

            _isLoading.emit(true)

            delay(300)

            when (recipe.isFavorite) {
                true -> {
                    _recipe.emit(recipe.copy(isFavorite = false))
                    recipeRepository.removeRecipe(recipe)
                    if (source.source == SourceType.SUGGESTED.name) recipeRepository.updateSuggestedRecipe(recipe.copy(isFavorite = false).toSuggestedRecipe())
                }

                else -> {
                    _recipe.emit(recipe.copy(isFavorite = true))
                    recipeRepository.insertRecipe(recipe.copy(isFavorite = true))
                    if (source.source == SourceType.SUGGESTED.name) recipeRepository.updateSuggestedRecipe(recipe.copy(isFavorite = true).toSuggestedRecipe())
                }
            }
            _isLoading.emit(false)
        }
    }

    fun getNumberedRecipeSteps(steps: List<String>): List<String> {
        val numberedSteps = steps.mapIndexed { index, step ->
            "${index + 1}. $step"
        }

        return numberedSteps
    }
}