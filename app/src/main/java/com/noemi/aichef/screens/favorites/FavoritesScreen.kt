package com.noemi.aichef.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.noemi.aichef.R
import com.noemi.aichef.model.FavoritesUIEvent
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.util.AIChefProgressIndicator
import com.noemi.aichef.util.EmptyResult
import com.noemi.aichef.util.showSnackBar

@Composable
fun FavoritesScreen(snackBarHostState: SnackbarHostState, modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<FavoritesViewModel>()
    val favoritesState by viewModel.favoritesState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (favoritesState.isLoading) {
            true -> AIChefProgressIndicator()
            else -> FavoritesRecipesContent(recipes = favoritesState.recipes, viewModel = viewModel)
        }

        if (favoritesState.hasError.isNotEmpty()) showSnackBar(scope = scope, message = favoritesState.hasError, snackbarHostState = snackBarHostState)
    }
}

@Composable
fun FavoritesRecipesContent(
    recipes: List<Recipe>,
    viewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    val lazyState = rememberLazyListState()
    LazyColumn(
        state = lazyState
    ) {

        item {
            Spacer(modifier = modifier.padding(12.dp))
        }

        if (recipes.isEmpty()) item { EmptyResult(R.string.label_empty_favorites_screen) }

        items(
            items = recipes,
            key = { recipe -> recipe.name }
        ) { recipe ->
            AIChefFavoriteRecipe(recipe = recipe, onRecipeStateChanged = {
                viewModel.onEvent(FavoritesUIEvent.Delete(recipe))
            })
        }
    }
}