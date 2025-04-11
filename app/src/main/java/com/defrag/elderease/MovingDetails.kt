package com.defrag.elderease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defrag.elderease.ui.theme.ElderEaseTheme

data class CircleStep(
    val title: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean
)

class MovingDetails : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    var currentStep by remember { mutableIntStateOf(0) }
    val totalSteps = 4

    // Derive the state of circleSteps based on currentStep
    val circleSteps = remember(currentStep) {
        List(totalSteps) { index ->
            CircleStep(
                title = "Step ${index + 1}",
                isCompleted = index < currentStep,
                isCurrent = index == currentStep
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Moving Details", color = Color.Black, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.LightGray
                )
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
                        .padding(16.dp)
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
            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(circleSteps.size) { index ->
                    CircleItem(step = circleSteps[index])
                }
            }

            // Page Content
            Spacer(modifier = Modifier.height(16.dp))
            // Page Content based on the current step
            when (currentStep) {
                0 -> Page1Content()
                1 -> Page2Content()
                2 -> Page3Content()
                3 -> Page4Content()
            }
        }
    }
}

@Composable
fun CircleItem(step: CircleStep) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    when {
                        step.isCurrent -> Color.Green
                        step.isCompleted -> Color.LightGray
                        else -> Color.Transparent // Unfilled
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (step.isCompleted) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Completed",
                    tint = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = step.title, fontSize = 12.sp)
    }
}

@Composable
fun Page1Content() {
    Text("Page 1 Content")
}

@Composable
fun Page2Content() {
    Text("Page 2 Content")
}

@Composable
fun Page3Content() {
    Text("Page 3 Content")
}

@Composable
fun Page4Content() {
    Text("Page 4 Content")
}