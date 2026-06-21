package com.keepsake.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.keepsake.app.data.Recipe
import com.keepsake.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookbookScreen(
    viewModel: CookbookViewModel = viewModel(),
    onRecipeClick: (Long) -> Unit,
    onAddClick: () -> Unit
) {
    val recipes by viewModel.recipes.collectAsState(initial = emptyList())

    Scaffold(
        containerColor = Parchment,
        topBar = {
            Column(Modifier.padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 8.dp)) {
                Text("Keepsake", style = MaterialTheme.typography.displayLarge, color = Ink)
                Text(
                    "${recipes.size} recipe${if (recipes.size == 1) "" else "s"} kept",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkSoft
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Wine,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add a recipe")
            }
        }
    ) { padding ->
        if (recipes.isEmpty()) {
            EmptyCookbook(Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(recipes, key = { it.id }) { recipe ->
                    RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
                }
                item { Spacer(Modifier.height(72.dp)) }
            }
        }
    }
}

@Composable
private fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Card)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(WineSoft),
            contentAlignment = Alignment.Center
        ) {
            if (recipe.photoPath != null) {
                AsyncImage(
                    model = recipe.photoPath,
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(Icons.Filled.MenuBook, contentDescription = null, tint = Wine)
            }
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(recipe.title, style = MaterialTheme.typography.titleMedium, color = Ink)
            Spacer(Modifier.height(2.dp))
            Text("from ${recipe.contributor}", style = MaterialTheme.typography.bodyMedium, color = InkSoft)
            if (recipe.tag.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Box(
                    Modifier
                        .clip(RoundedCornerShape(100))
                        .background(HerbSoft)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(recipe.tag, style = MaterialTheme.typography.labelSmall, color = Herb)
                }
            }
        }
    }
}

@Composable
private fun EmptyCookbook(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Filled.MenuBook, contentDescription = null, tint = Wine, modifier = Modifier.size(40.dp))
        Spacer(Modifier.height(16.dp))
        Text(
            "Your cookbook is empty",
            style = MaterialTheme.typography.headlineMedium,
            color = Ink
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Tap the + button to save your first family recipe — a photo of the card, and the story behind it.",
            style = MaterialTheme.typography.bodyLarge,
            color = InkSoft,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
