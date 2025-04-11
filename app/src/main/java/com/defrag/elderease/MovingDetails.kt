package com.defrag.elderease

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.defrag.elderease.ui.theme.ElderEaseTheme

data class CircleStep(
    val title: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean,
    val icon: Int? = null // Add icon property, making it nullable
)

class MovingDetails : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElderEaseTheme {
                MovingDetailsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovingDetailsScreen() {
    var currentStep by remember { mutableStateOf(0) }
    val totalSteps = 4
    val context = LocalContext.current
    val stepTitles = listOf(
        "Location",
        "Add Items",
        "Add-Ons",
        "Review"
    )
    val stepIcons = listOf<Int?>(
        R.drawable.location,
        R.drawable.add_items,
        R.drawable.addon,
        R.drawable.review
    )

    // Derive the state of circleSteps based on currentStep
    val circleSteps = remember(currentStep) {
        List(totalSteps) { index ->
            CircleStep(
                title = stepTitles[index],
                isCompleted = index < currentStep,
                isCurrent = index == currentStep,
                icon = stepIcons[index]
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stepTitles[currentStep], // Dynamically update the title
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = LightGray
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Black
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = {
                        if (currentStep < totalSteps - 1) {
                            currentStep++
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF065D58)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Confirm")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circles Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                circleSteps.forEachIndexed { index, step ->
                    CircleItem(
                        step = step,
                        onClick = {
                            currentStep = index
                        }
                    )
                }
            }

            // Page Content
            Spacer(modifier = Modifier.height(16.dp))
            // Page Content based on the current step
            when (currentStep) {
                0 -> Page1Content(title = stepTitles[0])
                1 -> Page2Content(title = stepTitles[1])
                2 -> Page3Content(title = stepTitles[2])
                3 -> Page4Content(title = stepTitles[3])
            }
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

@Composable
fun Page1Content(title: String) {
    Text(title)
}

@Composable
fun Page2Content(title: String) {
    Text(title)
}

@Composable
fun Page3Content(title: String) {
    Text(title)
}

@Composable
fun Page4Content(title: String) {
    Text(title)
}