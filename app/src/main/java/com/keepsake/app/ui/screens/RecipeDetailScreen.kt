package com.keepsake.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.keepsake.app.data.Recipe
import com.keepsake.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Long,
    viewModel: RecipeDetailViewModel = viewModel(),
    onBack: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val recipe by produceState<Recipe?>(initialValue = null, recipeId) {
        value = viewModel.load(recipeId)
    }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Parchment,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
                    }
                },
                actions = {
                    recipe?.let { r ->
                        IconButton(onClick = { onEdit(r.id) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Ink)
                        }
                        IconButton(onClick = { showDeleteConfirm = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Ink)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Parchment)
            )
        }
    ) { padding ->
        recipe?.let { r ->
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 220.dp, max = 420.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(WineSoft),
                    contentAlignment = Alignment.Center
                ) {
                    if (r.photoPath != null) {
                        AsyncImage(
                            model = r.photoPath,
                            contentDescription = r.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Icon(Icons.Filled.MenuBook, contentDescription = null, tint = Wine, modifier = Modifier.size(48.dp))
                    }
                }

                Spacer(Modifier.height(20.dp))
                Text(r.title, style = MaterialTheme.typography.displayLarge, color = Ink)
                Spacer(Modifier.height(4.dp))
                Text("from ${r.contributor}", style = MaterialTheme.typography.bodyLarge, color = InkSoft)

                if (r.story.isNotBlank()) {
                    Spacer(Modifier.height(18.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Card)
                            .padding(18.dp)
                    ) {
                        Column {
                            Text(
                                "\u201C${r.story}\u201D",
                                style = MaterialTheme.typography.labelLarge,
                                color = Ink
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "— ${r.contributor}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = InkSoft,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                }

                if (r.ingredients.isNotBlank()) {
                    SectionBlock(title = "Ingredients", body = r.ingredients)
                }
                if (r.instructions.isNotBlank()) {
                    SectionBlock(title = "Instructions", body = r.instructions)
                }

                Spacer(Modifier.height(48.dp))
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Remove this recipe?") },
            text = { Text("This can't be undone. The photo and story will be deleted from your cookbook.") },
            confirmButton = {
                TextButton(onClick = {
                    recipe?.let { viewModel.delete(it) }
                    showDeleteConfirm = false
                    onBack()
                }) { Text("Remove", color = Wine) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun SectionBlock(title: String, body: String) {
    Spacer(Modifier.height(22.dp))
    Text(title, style = MaterialTheme.typography.headlineMedium, color = Ink)
    Spacer(Modifier.height(8.dp))
    Text(body, style = MaterialTheme.typography.bodyLarge, color = Ink)
}
