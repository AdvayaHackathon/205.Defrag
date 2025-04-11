package com.defrag.elderease.ui.theme

import androidx.compose.ui.text.googlefonts.GoogleFont
import com.defrag.elderease.R
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)


val fontName = GoogleFont("Inter", bestEffort = true)

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)