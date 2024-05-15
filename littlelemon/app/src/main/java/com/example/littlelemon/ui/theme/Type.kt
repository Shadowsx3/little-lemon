package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R


val karla = FontFamily(Font(R.font.karla_regular))
val markazi = FontFamily(Font(R.font.markazi_text_regular))

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = karla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    titleLarge = TextStyle(
        fontFamily = markazi,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),

    titleMedium = TextStyle(
        fontFamily = markazi,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        color = HighlightGray
    ),

    titleSmall = TextStyle(
        fontFamily = karla,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = PrimaryGreen
    ),
)