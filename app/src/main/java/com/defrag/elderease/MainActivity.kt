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

