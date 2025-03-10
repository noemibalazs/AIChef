package com.noemi.aichef.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noemi.aichef.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChefAppBar(title: String, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.logo_icon),
                contentDescription = stringResource(id = R.string.label_icon_content_description),
                modifier = modifier
                    .padding(start = 12.dp, end = 8.dp)
                    .size(32.dp),
                tint = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier.testTag(stringResource(id = R.string.label_app_bar_tag))
    )
}

@Composable
fun AIChefProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            modifier = modifier.size(60.dp),
            strokeWidth = 6.dp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

fun showSnackBar(snackbarHostState: SnackbarHostState, scope: CoroutineScope, message: String) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
    }
}

@Composable
fun EmptyResult(textRes: Int) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = textRes),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            textAlign = TextAlign.Center
        )
    }
}

fun getRecipeDrawable(index: Int): Int {
    val drawables = listOf(R.drawable.farfale, R.drawable.shrimp, R.drawable.steak)
    return drawables[index]
}
