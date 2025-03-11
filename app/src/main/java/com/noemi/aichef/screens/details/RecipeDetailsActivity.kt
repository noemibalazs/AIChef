package com.noemi.aichef.screens.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.noemi.aichef.model.Source
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.ui.theme.AIChefTheme
import com.noemi.aichef.util.Constants.KEY_RECIPE
import com.noemi.aichef.util.Constants.KEY_SOURCE
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class RecipeDetailsActivity : ComponentActivity() {

    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val recipe = intent?.getParcelableExtra(KEY_RECIPE) ?: Recipe()
        val source = intent?.getParcelableExtra(KEY_SOURCE) ?: Source()
        viewModel.onRecipeReceived(recipe)

        setContent {
            AIChefTheme {
                RecipeDetailsScreen(viewModel, source)
            }
        }
    }

    companion object {

        fun startDetailsActivity(context: Context, recipe: Recipe, source: Source) {
            val intent = Intent(context, RecipeDetailsActivity::class.java)
            intent.putExtra(KEY_RECIPE, recipe)
            intent.putExtra(KEY_SOURCE, source)
            context.startActivity(intent)
        }
    }
}
