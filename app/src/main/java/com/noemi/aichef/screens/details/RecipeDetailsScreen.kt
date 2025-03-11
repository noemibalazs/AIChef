package com.noemi.aichef.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.noemi.aichef.R
import com.noemi.aichef.model.RecipeDetailsUIEvent
import com.noemi.aichef.model.Source
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.util.AIChefProgressIndicator

@Composable
fun RecipeDetailsScreen(viewModel: RecipeDetailsViewModel, source: Source, modifier: Modifier = Modifier) {

    val recipe by viewModel.recipe.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        when (isLoading) {
            true -> AIChefProgressIndicator()
            else -> DetailsScreen(recipe = recipe, viewModel = viewModel, source = source)
        }
    }
}

@Composable
fun DetailsScreen(
    recipe: Recipe,
    source: Source,
    viewModel: RecipeDetailsViewModel,
    modifier: Modifier = Modifier
) {

    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        modifier = modifier
    ) {

        item {
            RecipeImage(imageIndex = recipe.index)
        }

        item {
            RecipeNameWithIcon(
                name = recipe.name,
                onStateChanged = {
                    viewModel.onEvent(RecipeDetailsUIEvent.StateChanged(recipe, source))
                },
                isFavorite = recipe.isFavorite
            )
        }

        item {
            RecipeHeadline(text = stringResource(id = R.string.label_description))
        }

        item {
            RecipeDescription(description = recipe.description)
        }

        item {
            RecipeHeadline(text = stringResource(id = R.string.label_ingredients))
        }

        items(
            items = recipe.ingredients.ingredients,
            key = { ingredient ->
                ingredient.hashCode()
            }
        ) { ingredient ->
            RecipeIngredient(ingredient = ingredient)
        }

        item {
            RecipeHeadline(text = stringResource(id = R.string.label_instructions))
        }

        items(
            items = viewModel.getNumberedRecipeSteps(recipe.steps.steps),
            key = { step ->
                step.hashCode()
            }
        ) { step ->
            RecipeStep(step = step)
        }

        item {
            Spacer(modifier = modifier.padding(bottom = 48.dp))
        }
    }
}

