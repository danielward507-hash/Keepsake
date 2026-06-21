package com.keepsake.app.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.keepsake.app.data.RecipeRepository

class CookbookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecipeRepository(application)
    val recipes = repository.getAll()
}
