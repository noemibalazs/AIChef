package com.noemi.aichef.screens.suggested

import androidx.lifecycle.viewModelScope
import com.noemi.aichef.base.BaseViewModel
import com.noemi.aichef.model.SuggestedUIEvent
import com.noemi.aichef.repository.RecipeRepository
import com.noemi.aichef.room.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestedRecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel<SuggestedUIEvent>() {

    private val _recipesState = MutableStateFlow(SuggestedRecipesState())
    val recipesState = _recipesState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        onEvent(SuggestedUIEvent.Search)
    }

    override fun handleEvent(event: SuggestedUIEvent) {
        when (event) {
            is SuggestedUIEvent.Search -> loadRecipesWhenUserTyping()
            is SuggestedUIEvent.Disapprove -> loadMoreSuggestedRecipes()
            is SuggestedUIEvent.StateChanged -> recipeStateChanged(event.recipe)
        }
    }

    fun onTextChanged(text: String) {
        if (text.isEmpty()) onEvent(SuggestedUIEvent.Search)
        _searchText.value = text
    }

    @OptIn(FlowPreview::class)
    private fun loadRecipesWhenUserTyping() {
        _searchText
            .debounce(600)
            .filter { query -> query.length > 5 }
            .onEach {
                _recipesState.update {
                    it.copy(isLoading = true)
                }

                delay(600)
            }.zip(
                recipeRepository.loadSuggestedRecipes(_searchText.value).catch { error ->
                    _recipesState.update {
                        it.copy(
                            isLoading = false,
                            hasError = error.message ?: "Error, try it again!",
                            recipes = emptyList()
                        )
                    }
                }) { _, result ->
                _recipesState.update {
                    it.copy(
                        isLoading = false,
                        hasError = "",
                        recipes = result
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun loadMoreSuggestedRecipes() {
        viewModelScope.launch {

            _recipesState.update {
                it.copy(
                    isLoading = true
                )
            }

            delay(900)

            val recipes = mutableListOf(recipesState.value.recipes)

            recipeRepository.loadSuggestedRecipes(_searchText.value)
                .catch { error ->

                    _recipesState.update {
                        it.copy(
                            isLoading = false,
                            hasError = error.message ?: "Failed to load recipes, try it again!"
                        )
                    }
                }
                .collect { result ->

                    recipes.add(result)
                    _recipesState.update {
                        it.copy(
                            isLoading = false,
                            recipes = recipes.flatten(),
                            hasError = ""
                        )
                    }
                }
        }
    }

    private fun recipeStateChanged(recipe: Recipe) {
        viewModelScope.launch {

            _recipesState.update {
                it.copy(isLoading = true)
            }

            delay(600)

            val recipes = recipesState.value.recipes

            val updatedRecipes = recipes.map { result ->
                when (result == recipe) {
                    true -> {
                        if (recipe.isFavorite) {
                            recipeRepository.removeRecipe(recipe)
                            result.copy(isFavorite = false)
                        } else {
                            recipeRepository.insertRecipe(recipe.copy(isFavorite = true))
                            result.copy(isFavorite = true)
                        }
                    }

                    else -> result
                }
            }

            _recipesState.update {
                it.copy(
                    isLoading = false,
                    recipes = updatedRecipes
                )
            }
        }
    }

    data class SuggestedRecipesState(
        val isLoading: Boolean = false,
        val hasError: String = "",
        val recipes: List<Recipe> = emptyList()
    )
}