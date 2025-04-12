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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defrag.elderease.ui.theme.ElderEaseTheme

data class CircleStep(
    val title: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean,
    val icon: Int? = null // Add icon property, making it nullable
)

//Add this function at the end of the file, outside all the composables and before the class
fun getItemById(itemId: Int): CheckboxItem? {
    val items = listOf(
        CheckboxItem(id = 1, description = "Need 1 additional person to shift the patient", icon = R.drawable.icon1, cost = 100.0),
        CheckboxItem(id = 2, description = "Need a wheelchair", icon = R.drawable.icon2, cost = 500.0),
        CheckboxItem(id = 3, description = "Oxygen support", icon = R.drawable.icon3, cost = 1500.0),
        CheckboxItem(id = 4, description = "Medical transfer (includes stretcher, monitor, drip stand, oxygen)", icon = R.drawable.icon4, cost = 200.0),
        CheckboxItem(id = 5, description = "Need help with hospital paperwork", icon = R.drawable.icon5, cost = 800.0),
        CheckboxItem(id = 6, description = "Need post-visit pharmacy pick-up", icon = R.drawable.icon6, cost = 700.0),
    )
    return items.find { it.id == itemId }
}

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
    var showBottomSheet by remember { mutableStateOf(false) }

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

    var selectedItems by remember { mutableStateOf(emptySet<Int>()) }
    val selectedItemCount = selectedItems.size
    var selectedAddOns by remember { mutableStateOf<List<AddOnItem>>(emptyList()) }
    val totalCost = calculateTotalCost(selectedItems, selectedAddOns)

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
        modifier = Modifier.systemBarsPadding(),
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
                    White
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
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.1f)
            ) // Add the top border here
            .fillMaxWidth()
            .height(90.dp)
            .background(White)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentStep == 1) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "$selectedItemCount items added",
                        color = LightGray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start
                    )
                    Row(
                        modifier = Modifier.clickable { showBottomSheet = true },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "View all",
                            color = Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "View All",
                            tint = Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            if(currentStep == 2){
                val totalCost = calculateTotalCost(selectedItems)
                val selectedItemCount = selectedItems.size
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "₹$totalCost",
                        color = Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "$selectedItemCount items selected",
                        color = LightGray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start
                    )

                }
            }

            ConfirmButton(
                currentStep = currentStep,
                totalSteps = totalSteps,
                onCurrentStepChanged = { newStep -> currentStep = newStep })
        }
    }
}
    ) { innerPadding ->

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }, // Close on dismiss
                sheetState = rememberModalBottomSheetState(),
            ) {
                // Bottom sheet content
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    if (selectedItems.isEmpty()) {
                        item {
                            Text(
                                text = "No items selected",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        items(selectedItems.toList()) { itemId ->
                            // Find the corresponding item based on ID
                            val item = getItemById(itemId)
                            if (item != null) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = item.description,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.size(16.dp))
                                    Text(text = item.description)
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
            // ... Content of the screen...

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circles Row
            Row(
                modifier = Modifier
                    .background(if (currentStep == 2 || currentStep == 3) White else Transparent)
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
                1 -> Page2Content(
        title = stepTitles[1],
        selectedItems = selectedItems, // Pass the variable here
        onItemSelectedChange = { newItems -> selectedItems = newItems } // Pass the lambda here
    )
                2 -> {
                    Page3Content(
                        title = stepTitles[currentStep],
                        selectedItems = selectedItems,
                        totalCost = totalCost,
                        onAddOnsChanged = { newAddOns ->
                            selectedAddOns = newAddOns
                        }
                    )
                }
    3 -> Page4Content(totalCost)
                4 -> Page5Content()
}
        }
    }
}









@Composable
fun Page4Content(totalCost: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add spacing between cards
    ) {
        // Referral/Coupon Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Handle click */ },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F2F2),
                contentColor = Black
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.coupon), // Replace with your icon
                    contentDescription = "Referral Code",
                    modifier = Modifier.size(24.dp),
                    tint = Black
                )
                Text(
                    text = "Enter Referral or Coupon Code",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        // Payment Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Black
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Payment Summary",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Quoted Amount
                PaymentRow(label = "Quoted Amount", cost = "₹1000.0")
                Divider(color = Gray, modifier = Modifier.padding(vertical = 8.dp))

                var totalmoney = totalCost + 500
                // Total Amount to be Paid
                PaymentRow(label = "Total Amount to be Paid", cost = "₹$totalmoney")
                Divider(color = Gray, modifier = Modifier.padding(vertical = 8.dp))

                // Booking Amount to be Paid
                PaymentRow(label = "Booking Amount to be Paid", cost = "₹500.0")
                Text(
                    text = "(An adjustable amount of Rs. 500 needs to be paid for order confirmation)",
                    color = Gray,

                )
                Divider(color = Gray, modifier = Modifier.padding(vertical = 8.dp))

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "*By confirming, you agree to the terms and conditions.",
                    fontSize = 12.sp,
                    color = Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun PaymentRow(label: String, cost: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = cost,
            color = Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.End
        )
    }
}