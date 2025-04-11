package com.defrag.elderease

import android.R.attr.tag
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page1Content(title: String) {
    val uriHandler = LocalUriHandler.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

            var pickupLocation by remember { mutableStateOf("") }
            var dropLocation by remember { mutableStateOf("") }
            var dateTime by remember { mutableStateOf("") }

            OutlinedTextField(
                value = pickupLocation,
                onValueChange = { pickupLocation = it },
                label = { Text("Enter Pickup Location") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.pickup),
                        contentDescription = "Pickup Location Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
            //Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = dropLocation,
                onValueChange = { dropLocation = it },
                label = { Text("Enter Drop Location") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.dropoff),
                        contentDescription = "Drop Location Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
            //Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = dateTime,
                onValueChange = { dateTime = it },
                label = { Text("Preferred Date and Time") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Date and Time Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Radio Button Boxes
            var selectedTripType by remember { mutableStateOf("oneWay") } // Default to one-way

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(0.dp) // No spacing between boxes
            ) {
                TripTypeBox(
                    title = "One-Way",
                    subtitle = "Get dropped off",
                    isSelected = selectedTripType == "oneWay",
                    onSelect = { selectedTripType = "oneWay" },
                    isFirst = true,
                    modifier = Modifier.weight(1f)
                )
                TripTypeBox(
                    title = "Round Trip",
                    subtitle = "Keep the vehicle till return",
                    isSelected = selectedTripType == "roundTrip",
                    onSelect = { selectedTripType = "roundTrip" },
                    isFirst = false,
                    modifier = Modifier
                        .weight(1f)
                        .offset(x = (-1).dp) // Using offset to achieve the overlap

                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            val annotatedText = buildAnnotatedString {
                val tag = "Clickable"
                pushStringAnnotation(
                    tag = tag,
                    annotation = "https://www.google.com"
                ) // We add the tag for the click
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Black
                    )
                ) {
                    append("Need help with check-in/out process?")
                }
                pop()
            }
            Text(
                modifier = Modifier.clickable {
                    annotatedText.getStringAnnotations(tag.toString(), 0, annotatedText.length)
                        .firstOrNull()?.let {
                            uriHandler.openUri(it.item) // We open the link
                        }
                },
                text = annotatedText
            )

            Spacer(modifier = Modifier.height(16.dp))
            // add a big box here that fills the width and is a map of your location
            Image(
                painter = painterResource(id = R.drawable.map), // Replaced the Box with Image
                contentDescription = "Map Placeholder",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .shadow(1.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TripTypeBox(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    isFirst: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) Black else Color.LightGray,
                shape = if (isFirst) RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp
                ) else RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
            )
            .background(Transparent)
            .clickable(onClick = onSelect)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = subtitle, fontSize = 12.sp)
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .background(Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Selected",
                    tint = White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


// Data class for the checkbox list items
data class CheckboxItem(
    val id: Int,
    val description: String,
    val icon: Int,
    val cost: Double // Add this line
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page2Content(
    title: String,
    selectedItems: Set<Int>, // Receive selected items
    onItemSelectedChange: (Set<Int>) -> Unit // Receive the lambda to change items
) {
    val items = listOf(
        CheckboxItem(id = 1, description = "Need 1 additional person to shift the patient", icon = R.drawable.icon1, cost = 100.0),
        CheckboxItem(id = 2, description = "Need a wheelchair", icon = R.drawable.icon2, cost = 100.0),
        CheckboxItem(id = 3, description = "Oxygen support", icon = R.drawable.icon3, cost = 100.0),
        CheckboxItem(id = 4, description = "Medical transfer (includes stretcher, monitor, drip stand, oxygen)", icon = R.drawable.icon4, cost = 100.0),
        CheckboxItem(id = 5, description = "Need help with hospital paperwork", icon = R.drawable.icon5, cost = 100.0),
        CheckboxItem(id = 6, description = "Need post-visit pharmacy pick-up", icon = R.drawable.icon6, cost = 100.0),
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // "Facilities" Title Item
        FacilitiesTitleItem(title = "Facilities")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { item ->
                CheckboxListItem(
                    item = item,
                    isSelected = selectedItems.contains(item.id),
                    onItemClicked = { isChecked ->
                        val newSelectedItems = if (isChecked) {
                            selectedItems + item.id
                        } else {
                            selectedItems - item.id
                        }
                        onItemSelectedChange(newSelectedItems)
                    }
                )
                if (item.id != items.last().id) {
                    Divider(color = LightGray, thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun FacilitiesTitleItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        //Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

    }
    Divider(color = LightGray, thickness = 1.dp)
}

@Composable
fun CheckboxListItem(
    item: CheckboxItem,
    isSelected: Boolean,
    onItemClicked: (Boolean) -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isClicked = !isClicked
                onItemClicked(!isSelected)
            }
            .background(if (isSelected || isClicked) LightGray.copy(alpha = 0.5f) else Transparent)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.description,
            modifier = Modifier.size(24.dp),
            tint = Black
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = item.description, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "More",
            tint = Black
        )
    }
}