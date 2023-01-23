package uz.uzkass.smartpos.supply.android.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.theme.SupplyColors

// Set of Material typography styles to start with


fun getTypography(colors: SupplyColors): Typography {
    return Typography(
        subtitle1 = TextStyle(
            color = colors.subtitle1,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),

        subtitle2 = TextStyle(
            color = colors.subtitle2,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),

        button = TextStyle(
            color = colors.buttonText,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    )
}