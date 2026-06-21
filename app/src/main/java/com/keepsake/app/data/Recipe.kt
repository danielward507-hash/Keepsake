package com.keepsake.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * One recipe in the cookbook. Everything lives on-device — there's no
 * account, no server, no sync. photoPath points to a JPEG saved under the
 * app's private files directory by the camera capture flow in AddEditRecipeScreen.
 */
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,              // e.g. "Sunday Pot Roast"
    val contributor: String,        // e.g. "Grandma Rose"
    val story: String,              // the memory/note behind the recipe
    val ingredients: String,        // free-form text, one per line
    val instructions: String,       // free-form text
    val tag: String,                // e.g. "Holiday", "Dessert", "Weeknight"
    val photoPath: String?,         // local file path to the recipe-card photo, if any
    val dateAdded: Long             // epoch millis
)
