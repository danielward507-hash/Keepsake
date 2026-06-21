package com.keepsake.app.ui.screens

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.keepsake.app.data.Recipe
import com.keepsake.app.data.RecipeRepository
import kotlinx.coroutines.launch
import java.io.File

/** Plain mutable holder for the form fields — kept simple on purpose, no extra state library needed. */
class RecipeFormState {
    var id: Long? = null
    var title by mutableStateOf("")
    var contributor by mutableStateOf("")
    var story by mutableStateOf("")
    var ingredients by mutableStateOf("")
    var instructions by mutableStateOf("")
    var tag by mutableStateOf("")
    var photoPath by mutableStateOf<String?>(null)
}

class AddEditRecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipeRepository(application)
    val state = RecipeFormState()

    fun loadForEdit(id: Long) {
        viewModelScope.launch {
            repository.getById(id)?.let { recipe ->
                state.id = recipe.id
                state.title = recipe.title
                state.contributor = recipe.contributor
                state.story = recipe.story
                state.ingredients = recipe.ingredients
                state.instructions = recipe.instructions
                state.tag = recipe.tag
                state.photoPath = recipe.photoPath
            }
        }
    }

    /** Creates a destination file for the camera app to write into, returns it plus its shareable Uri. */
    fun preparePhotoCapture(): Pair<File, Uri> = repository.createPhotoUri()

    fun save(onSaved: () -> Unit) {
        viewModelScope.launch {
            val recipe = Recipe(
                id = state.id ?: 0,
                title = state.title.ifBlank { "Untitled recipe" },
                contributor = state.contributor,
                story = state.story,
                ingredients = state.ingredients,
                instructions = state.instructions,
                tag = state.tag,
                photoPath = state.photoPath,
                dateAdded = System.currentTimeMillis()
            )
            if (state.id == null) {
                repository.save(recipe)
            } else {
                repository.update(recipe)
            }
            onSaved()
        }
    }
}
