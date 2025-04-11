package com.defrag.elderease

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils

@Composable
fun ConfirmButton(currentStep: Int, totalSteps: Int, onCurrentStepChanged: (Int) -> Unit) {
    val buttonModifier = if (currentStep == 1 || currentStep == 2) {
        Modifier
            .width(120.dp)
            .height(40.dp)
    } else {
        Modifier
            .fillMaxWidth()
            .height(40.dp)
    }
    Button(
        onClick = {
            if (currentStep < totalSteps - 1) {
                onCurrentStepChanged(currentStep + 1)
            }
        },
        modifier = buttonModifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF065D58)),
        shape = RoundedCornerShape(10.dp)
    ) {
        if (currentStep == 0 || currentStep == 1) {
            Text("Confirm")
        }
        else if (currentStep == 2){
            Text("Review booking")
        }
        else if (currentStep == 3){
            Text("Book Now")
        }
    }
}

@Composable
fun CircleItem(step: CircleStep, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (!step.isCurrent) {
                        Color(ColorUtils.setAlphaComponent(Black.toArgb(), (0.3f * 255).toInt()))
                    } else {
                        Transparent
                    },
                    shape = CircleShape
                )
                .background(
                    color = if (step.isCurrent) Color(0xFF065D58) else Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            if (step.isCompleted) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Completed",
                    tint = Color(ColorUtils.setAlphaComponent(Black.toArgb(), (0.3f * 255).toInt())) // Checkmark is now grayed out
                )
            } else if (step.icon != null) {
                if (step.icon is ImageVector) {
                    Icon(
                        imageVector = step.icon,
                        contentDescription = step.title,
                        tint = if (step.isCurrent) White else Black
                    )
                } else {
                    Icon(
                        painter = painterResource(id = step.icon),
                        modifier = Modifier.size(20.dp),
                        contentDescription = step.title,
                        tint = if (step.isCurrent) White else Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = step.title,
            fontSize = 12.sp,
            fontWeight = if (step.isCurrent) FontWeight.Bold else FontWeight.Normal,
            color = if (step.isCurrent) Black else Black
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientCardWithBottomSheet(
    context: Context, // Added context parameter
    title: String,
    subtitle: String,
    description: String,
    imageResId: Int,
    baseColor: Color = Color.Blue,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val darkerShade = baseColor.copy(alpha = 0.7f).compositeOver(Color.Black.copy(alpha = 0.4f))
    val iconPlaceholderColor = Color.Black.copy(alpha = 0.2f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { showBottomSheet = true },
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            baseColor,
                            darkerShade
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = subtitle,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Arrow Icon Container
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(iconPlaceholderColor)
                            .width(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Proceed",
                            tint = Color.White,

                            )
                    }

                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(iconPlaceholderColor),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Card Image",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            PopupContent(context) // Pass context here
        }
    }
}

@Composable
fun PopupContent(context: Context) {
    // Bottom sheet content
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose your service",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        GradientCardWithBorder(
            title = "Quick Visit",
            subtitle = "(Upto 2 hours)",
            description = "Perfect for a short consultation or follow-up.",
            baseColor = Color(0xFFCBDFF2),
            borderColor = Color(0xFF9DB7CB),
            context = context
        )
        GradientCardWithBorder(
            context = context,
            title = "Half Day Help",
            subtitle = "(Upto 4 hours)",
            description = "Ideal for slightly longer medical visits with waiting time.",
            baseColor = Color(0xFFBBE0C6),
            borderColor = Color(0xFF8EA792)
        )
        GradientCardWithBorder(
            context = context,
            title = "Full Day Care",
            subtitle = "(Upto 8 hours)",
            description = "Great for extended appointments or procedures.",
            baseColor = Color(0xFFE5CAC6),
            borderColor = Color(0xFFBA9E9A)
        )
    }
}




@Composable
fun GradientCardWithBorder(
    context: Context, // Added context parameter
    title: String,
    subtitle: String,
    description: String,
    baseColor: Color = Color.Blue,
    borderColor: Color = baseColor, // New borderColor parameter, defaults to baseColor
) {
    val darkerShade = baseColor.copy(alpha = 0.7f).compositeOver(Color.White.copy(alpha = 0.8f))
    val transparentBlack = Color.Black.copy(alpha = 0.2f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Launch MovingDetails activity
                val intent = Intent(context, MovingDetails::class.java)
                context.startActivity(intent)
            }
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            darkerShade,
                            baseColor
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = subtitle,
                            fontSize = 16.sp,
                            color = transparentBlack
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                }
                // Arrow Icon
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Proceed",
                        tint = borderColor
                    )
                }
            }
        }
    }
}

// In MovingDetails.kt

fun calculateTotalCost(selectedItems: Set<Int>, addOnItems: List<AddOnItem> = emptyList()): Double {
    var baseCost = 1000.0 // this will be the base cost
    selectedItems.forEach { itemId ->
        val item = getItemById(itemId)
        baseCost += item?.cost ?: 0.0
    }
    val addOnCost = addOnItems.sumOf { it.cost }
    return baseCost + addOnCost
}