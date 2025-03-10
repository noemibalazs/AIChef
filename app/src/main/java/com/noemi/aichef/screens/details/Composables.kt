package com.noemi.aichef.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noemi.aichef.R
import com.noemi.aichef.util.getRecipeDrawable

@Composable
fun RecipeImage(imageIndex: Int) {

    Image(
        painter = painterResource(id = getRecipeDrawable(imageIndex)),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RecipeNameWithIcon(
    name: String,
    isFavorite: Boolean,
    onStateChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(top = 12.dp, start = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = name,
            modifier = modifier
                .weight(3f)
                .padding( end = 8.dp),
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Image(
            painter = painterResource(id = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_lovable),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .size(36.dp)
                .clickable {
                    onStateChanged.invoke()
                },
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun RecipeHeadline(
    text: String,
    modifier: Modifier = Modifier
) {

    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        style = MaterialTheme.typography.headlineLarge.copy(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    )
}

@Composable
fun RecipeDescription(
    description: String,
    modifier: Modifier = Modifier
) {

    Text(
        text = description,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground
        ),
        textAlign = TextAlign.Justify,
    )
}

@Composable
fun RecipeIngredient(ingredient: String, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.dot),
            contentDescription = null,
            modifier = modifier
                .size(20.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = ingredient,
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground
            ),
            textAlign = TextAlign.Justify,
        )
    }
}

@Composable
fun RecipeStep(step: String, modifier: Modifier = Modifier) {

    Text(
        text = step,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground
        ),
        textAlign = TextAlign.Justify,
    )
}