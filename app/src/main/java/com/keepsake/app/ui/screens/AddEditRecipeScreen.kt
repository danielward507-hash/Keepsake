package com.keepsake.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.keepsake.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRecipeScreen(
    recipeId: Long?,
    viewModel: AddEditRecipeViewModel = viewModel(),
    onDone: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(recipeId) {
        if (recipeId != null) viewModel.loadForEdit(recipeId)
    }

    val state = viewModel.state
    var pendingPhotoFile by remember { mutableStateOf<java.io.File?>(null) }
    var pendingPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingPhotoFile?.let { viewModel.state.photoPath = it.absolutePath }
        }
    }

    Scaffold(
        containerColor = Parchment,
        topBar = {
            TopAppBar(
                title = { Text(if (recipeId == null) "New recipe" else "Edit recipe", color = Ink) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.save { onDone() }
                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "Save", tint = Wine)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Parchment)
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            // Photo capture
            // Photo capture
            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp, max = 380.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Card),
                contentAlignment = Alignment.Center
            ) {
                if (state.photoPath != null) {
                    AsyncImage(
                        model = state.photoPath,
                        contentDescription = "Recipe card photo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp))
                    )
                }
                FilledTonalButton(
                    onClick = {
                        val (file, uri) = viewModel.preparePhotoCapture()
                        pendingPhotoFile = file
                        pendingPhotoUri = uri
                        cameraLauncher.launch(uri)
                    },
                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = WineSoft, contentColor = Wine)
                ) {
                    Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (state.photoPath == null) "Photograph the recipe card" else "Retake photo")
                }
            }

            Spacer(Modifier.height(20.dp))
            LabeledField("Recipe name", state.title) { state.title = it }
            Spacer(Modifier.height(14.dp))
            LabeledField("Whose recipe is this?", state.contributor, placeholder = "e.g. Grandma Rose") { state.contributor = it }
            Spacer(Modifier.height(14.dp))
            LabeledField("Category", state.tag, placeholder = "e.g. Holiday, Dessert, Weeknight") { state.tag = it }
            Spacer(Modifier.height(14.dp))
            LabeledField(
                "The story behind it",
                state.story,
                placeholder = "What made this recipe special? A memory, a detail, anything worth keeping.",
                singleLine = false,
                minLines = 3
            ) { state.story = it }
            Spacer(Modifier.height(14.dp))
            LabeledField("Ingredients", state.ingredients, placeholder = "One per line", singleLine = false, minLines = 4) { state.ingredients = it }
            Spacer(Modifier.height(14.dp))
            LabeledField("Instructions", state.instructions, singleLine = false, minLines = 5) { state.instructions = it }

            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun LabeledField(
    label: String,
    value: String,
    placeholder: String = "",
    singleLine: Boolean = true,
    minLines: Int = 1,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(label, style = MaterialTheme.typography.titleMedium, color = Ink)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { if (placeholder.isNotEmpty()) Text(placeholder, color = InkSoft) },
            singleLine = singleLine,
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Wine,
                unfocusedBorderColor = Line,
                focusedContainerColor = Card,
                unfocusedContainerColor = Card
            )
        )
    }
}
