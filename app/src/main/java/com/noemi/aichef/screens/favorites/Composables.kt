package com.noemi.aichef.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noemi.aichef.R
import com.noemi.aichef.model.Source
import com.noemi.aichef.model.SourceType
import com.noemi.aichef.room.Recipe
import com.noemi.aichef.screens.details.RecipeDetailsActivity
import com.noemi.aichef.util.getRecipeDrawable

@Composable
fun AIChefFavoriteRecipe(
    recipe: Recipe,
    onRecipeStateChanged: (Recipe) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val width = LocalConfiguration.current.screenWidthDp.dp / 2

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(bottom = 8.dp)
    ) {
        Card(
            modifier = modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(6.dp)
                )
                .clickable {
                    RecipeDetailsActivity.startDetailsActivity(context, recipe, Source(SourceType.FAVORITE.name))
                },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.onPrimary)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = painterResource(id = getRecipeDrawable(recipe.index)),
                    contentDescription = stringResource(id = R.string.label_recipe_avatar),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .padding(horizontal = 12.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Column(
                    modifier = modifier.width(width),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Justify
                    )

                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Justify
                    )
                }


                Image(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = null,
                    modifier = modifier
                        .padding(12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            onRecipeStateChanged.invoke(recipe)
                        },
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }
        }
    }
}