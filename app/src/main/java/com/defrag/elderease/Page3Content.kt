package com.defrag.elderease

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Page3Content(
    title: String,
    selectedItems: Set<Int>,
    totalCost: Double,
    onAddOnsChanged: (List<AddOnItem>) -> Unit
) {
    var selectedAddOns by remember { mutableStateOf<List<AddOnItem>>(emptyList()) }

    // Add or remove an add on
    fun addOrRemoveAddOn(addOnItem: AddOnItem, isAdded: Boolean) {
        val newAddOns = if (isAdded) {
            selectedAddOns - addOnItem
        } else {
            selectedAddOns + addOnItem
        }
        selectedAddOns = newAddOns
        onAddOnsChanged(newAddOns)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8))
    ) {
        item {
            // Base Price Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Base Price Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Base Price",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "₹$totalCost",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    // Divider
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Details Section
                    val detailItems = listOf(
                        "Trained and empathetic assistant",
                        "Transportation to and from clinic/hospital",
                        "Appointment coordination",
                        "Basic documentation support"
                    )
                    detailItems.forEach { detail ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Tick",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(text = detail, fontSize = 14.sp)
                        }
                    }
                }
            }

            // Recommended Add-Ons Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Recommended Add-Ons
                    Text(
                        text = "Recommended Add-Ons",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Boost comfort, safety, and ease with these optional services",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Add-On Items
                    val addOnItems = listOf(
                        AddOnItem(
                            title = "Extra Ease",
                            cost = 300.0,
                            description = "Additional helper for physical support or high-need tasks"
                        ),
                        AddOnItem(
                            title = "Snack and Hydration Kit",
                            cost = 149.0,
                            description = "Healthy refreshments during travel or long visits"
                        ),
                        AddOnItem(
                            title = "Family Connect Video Update",
                            cost = 199.0,
                            description = "Short video summary sent to family after service"
                        )
                    )
                    addOnItems.forEachIndexed { index, item ->
                        AddOnItemRow(
                            item = item,
                            onAddClicked = { addOnItem, isAdded -> addOrRemoveAddOn(addOnItem, isAdded) }
                        )
                        if (index < addOnItems.size - 1) {
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

data class AddOnItem(
    val title: String,
    val cost: Double,
    val description: String
)

@Composable
fun AddOnItemRow(item: AddOnItem, onAddClicked: (AddOnItem, Boolean) -> Unit) {
    var isAdded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "+₹${item.cost}",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.description,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
        }

        Button(
            onClick = {
                isAdded = !isAdded
                onAddClicked(item, isAdded)
            },
            modifier = Modifier
                .width(100.dp)
                .height(32.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAdded) Color.LightGray else Color.Transparent,
                contentColor = Color.Black
            ),
            border = ButtonDefaults.outlinedButtonBorder
        ) {
            Text(
                text = if (isAdded) "Remove" else "Add",
                fontSize = 12.sp
            )
        }
    }


}

@Composable
fun Page5Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Thank You for Booking!",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        // You can add more details or a confirmation number here if needed
    }
}
