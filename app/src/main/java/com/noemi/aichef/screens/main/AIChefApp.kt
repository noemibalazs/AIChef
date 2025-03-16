package com.noemi.aichef.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.noemi.aichef.navigation.ChefDestination
import com.noemi.aichef.screens.favorites.FavoritesScreen
import com.noemi.aichef.screens.suggested.SuggestedRecipes
import com.noemi.aichef.ui.theme.AIChefTheme
import com.noemi.aichef.util.AIChefAppBar

@Composable
fun AIChefApp(modifier: Modifier = Modifier) {

    val snackBarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    var title by remember { mutableStateOf("") }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            title = entry.destination.route.toString().lowercase().replaceFirstChar(Char::titlecase)
        }
    }

    AIChefTheme {

        Scaffold(
            topBar = {
                AIChefAppBar(title = title)
            },
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            },
            modifier = modifier
                .fillMaxSize()
                .background(colorScheme.onPrimary)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            bottomBar = {
                AIChefBottomNavigationBar(navController)
            }
        ) { padding ->
            Column(
                modifier = modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .imePadding()
            ) {
                AIChefNavHost(navController, snackBarHostState)
            }
        }
    }
}

@Composable
private fun AIChefNavHost(navController: NavHostController, snackBarHostState: SnackbarHostState) {

    NavHost(navController = navController, startDestination = ChefDestination.SUGGESTED.name) {
        composable(ChefDestination.SUGGESTED.name) {
            SuggestedRecipes(snackBarHostState = snackBarHostState)
        }

        composable(ChefDestination.FAVORITE.name) {
            FavoritesScreen(snackBarHostState = snackBarHostState)
        }
    }
}

@Composable
private fun AIChefBottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val destinations = ChefDestination.getDestinations()

    NavigationBar(
        containerColor = colorScheme.onSecondaryContainer
    ) {

        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        destinations.forEach { destination ->

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorScheme.onPrimaryContainer,
                    selectedIconColor = colorScheme.onPrimary
                ),
                label = {
                    Text(
                        text = stringResource(destination.title),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                        color = colorScheme.onPrimaryContainer
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        modifier = modifier.size(24.dp),
                        contentDescription = null
                    )
                },
                selected = currentRoute == destination.name,
                onClick = {

                    navController.navigate(destination.name) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}