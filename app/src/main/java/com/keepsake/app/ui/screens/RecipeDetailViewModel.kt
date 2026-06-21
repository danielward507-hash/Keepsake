package com.keepsake.app.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.keepsake.app.data.Recipe
import com.keepsake.app.data.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipeRepository(application)

    suspend fun load(id: Long): Recipe? = repository.getById(id)

    fun delete(recipe: Recipe) {
        viewModelScope.launch { repository.delete(recipe) }
    }
}
