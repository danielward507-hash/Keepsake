package com.keepsake.app.data

import android.content.Context
import androidx.core.content.FileProvider
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecipeRepository(private val context: Context) {

    private val dao = KeepsakeDatabase.getInstance(context).recipeDao()

    fun getAll(): Flow<List<Recipe>> = dao.getAll()

    fun search(query: String): Flow<List<Recipe>> = dao.search(query)

    suspend fun getById(id: Long): Recipe? = dao.getById(id)

    suspend fun save(recipe: Recipe): Long = dao.upsert(recipe)

    suspend fun update(recipe: Recipe) = dao.update(recipe)

    suspend fun delete(recipe: Recipe) = dao.delete(recipe)

    /**
     * Creates a destination file (and its content:// Uri) for the system
     * camera app to write a recipe-card photo into. Keeps photos private
     * to this app's storage — nothing is shared elsewhere.
     */
    fun createPhotoUri(): Pair<File, android.net.Uri> {
        val dir = File(context.filesDir, "recipe_photos").apply { mkdirs() }
        val name = "recipe_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
        val file = File(dir, name)
        val uri = FileProvider.getUriForFile(context, "com.keepsake.app.fileprovider", file)
        return file to uri
    }
}
