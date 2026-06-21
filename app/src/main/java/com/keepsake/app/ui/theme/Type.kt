package com.keepsake.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Type pairing is deliberate: a slab serif for recipe titles (the way a
// recipe-box card title reads), a clean sans for the practical text
// (ingredients, instructions), and an italic accent reserved ONLY for the
// "her words" story quote on each recipe — like a note in the margin.
// Swap FontFamily.Serif/SansSerif for bundled Source Serif / Work Sans /
// Caveat .ttf files under res/font once you're ready to finalize the brand.

val TitleSerif = FontFamily.Serif
val BodySans = FontFamily.SansSerif

val KeepsakeTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = TitleSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = TitleSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = BodySans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = BodySans,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 23.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = BodySans,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 19.sp
    ),
    // Used ONLY for the story / margin-note quote on a recipe — the one
    // intentional flourish, kept rare so it stays meaningful.
    labelLarge = TextStyle(
        fontFamily = TitleSerif,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 25.sp
    ),
    labelSmall = TextStyle(
        fontFamily = BodySans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 14.sp
    )
)
