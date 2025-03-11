package com.noemi.aichef.screens.suggested

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
import com.noemi.aichef.model.SuggestedUIEvent
import com.noemi.aichef.room.SuggestedRecipe
import com.noemi.aichef.util.AIChefProgressIndicator
import com.noemi.aichef.util.EmptyResult
import com.noemi.aichef.util.showSnackBar

@Composable
fun SuggestedRecipes(snackBarHostState: SnackbarHostState, modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<SuggestedViewModel>()

    val recipesState by viewModel.recipesState.collectAsStateWithLifecycle()
    val promptText by viewModel.searchText.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AIChefSearch(
            promptText = promptText,
            onPromptChanged = viewModel::onTextChanged,
            modifier = modifier,
            isLoading = recipesState.isLoading
        )

        SuggestedRecipesContent(
            recipes = recipesState.recipes,
            viewModel = viewModel,
            promptText = promptText
        )

        if (recipesState.isLoading) AIChefProgressIndicator()

        if (recipesState.hasError.isNotEmpty()) showSnackBar(
            snackbarHostState = snackBarHostState, scope = scope, message = recipesState.hasError
        )
    }
}

@Composable
private fun SuggestedRecipesContent(
    recipes: List<SuggestedRecipe>,
    promptText: String,
    viewModel: SuggestedViewModel,
    modifier: Modifier = Modifier
) {
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState
    ) {

        item {
            Spacer(modifier = modifier.padding(12.dp))
        }

        if (recipes.isEmpty()) item { EmptyResult(R.string.label_empty_suggested_screen) }

        items(
            items = recipes,
            key = { recipe -> recipe }
        ) { recipe ->
            AIChefSuggestedRecipe(recipe = recipe, onRecipeStateChanged = {
                viewModel.onEvent(SuggestedUIEvent.StateChanged(recipe))
            })
        }

        item {
            Spacer(modifier = modifier.padding(12.dp))
        }

        item {
            AIChefCircularButton(
                onClick = { viewModel.onEvent(SuggestedUIEvent.Disapprove) },
                isEnabled = recipes.isNotEmpty() && promptText.length > 5
            )
        }

        item {
            Spacer(modifier = modifier.padding(20.dp))
        }
    }
}