package com.defrag.elderease

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defrag.elderease.ui.theme.ElderEaseTheme
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElderEaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ){
                        MainScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Scaffold() { contentPadding ->
        LazyColumn(modifier = Modifier.padding(contentPadding)) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Select your services",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                GradientCardWithBottomSheet(
                    context = context,
                    title = "Medical Visit",
                    subtitle = "Clinic | Hospital Visit",
                    description = "Need help with doctor visits or hospital support? We’ll take care of it from start to finish.",
                    imageResId = R.drawable.medical_services,
                    baseColor = Color(0xFF0A6F69),
                )


                GradientCardWithBottomSheet(
                    context = context,
                    title = "Travel Assistance",
                    subtitle = "Pickup & Drop Services",
                    description = "Smooth and safe travel for your loved ones whether it’s to or from stations, airports, or anywhere else.",
                    imageResId = R.drawable.travel,
                    baseColor = Color(0xFF5B71B9),
                )
                GradientCardWithBottomSheet(
                    context = context,
                    title = "Daily Support",
                    subtitle = "Banking | Shopping and Official Paperwork",
                    description = "From bank visits to grocery runs - we assist with everyday tasks, paperwork, and errands.",
                    imageResId = R.drawable.support,
                    baseColor = Color(0xFF67B173),
                )
                GradientCardWithBottomSheet(
                    context = context,
                    title = "Companionship Care",
                    subtitle = "Conversation | Light and Emotional Support",
                    description = "Someone to talk to, read with, or just spend time - our companions bring warmth and comfort.",
                    imageResId = R.drawable.companion,
                    baseColor = Color(0xFFD4764A),
                )
            }
        }


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