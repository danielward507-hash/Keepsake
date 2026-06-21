package com.keepsake.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.keepsake.app.ui.screens.AddEditRecipeScreen
import com.keepsake.app.ui.screens.CookbookScreen
import com.keepsake.app.ui.screens.RecipeDetailScreen

private const val ROUTE_COOKBOOK = "cookbook"
private const val ROUTE_DETAIL = "detail/{recipeId}"
private const val ROUTE_ADD = "add"
private const val ROUTE_EDIT = "edit/{recipeId}"

@Composable
fun KeepsakeNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ROUTE_COOKBOOK) {

        composable(ROUTE_COOKBOOK) {
            CookbookScreen(
                onRecipeClick = { id -> navController.navigate("detail/$id") },
                onAddClick = { navController.navigate(ROUTE_ADD) }
            )
        }

        composable(
            ROUTE_DETAIL,
            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("recipeId") ?: return@composable
            RecipeDetailScreen(
                recipeId = id,
                onBack = { navController.popBackStack() },
                onEdit = { editId -> navController.navigate("edit/$editId") }
            )
        }

        composable(ROUTE_ADD) {
            AddEditRecipeScreen(
                recipeId = null,
                onDone = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            ROUTE_EDIT,
            arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("recipeId") ?: return@composable
            AddEditRecipeScreen(
                recipeId = id,
                onDone = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
