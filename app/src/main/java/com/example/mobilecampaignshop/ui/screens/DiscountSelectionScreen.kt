package com.example.mobilecampaignshop.ui.screens

import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilecampaignshop.ui.theme.DiscountModuleTheme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilecampaignshop.models.Campaign
import com.example.mobilecampaignshop.models.DiscountBreakdown
import com.example.mobilecampaignshop.models.DiscountResult
import com.example.mobilecampaignshop.models.Product
import com.example.mobilecampaignshop.models.ProductCategory
import com.example.mobilecampaignshop.ui.components.ProductCard

@Composable
fun DiscountSelectionScreen(
    cartTotal: Double,
    discountResult: DiscountResult?,
    onApplyDiscounts: (List<Campaign>) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Fixed Amount values
    val fixedAmountValue = remember { mutableStateOf("") }
    val fixedAmountSelected = remember { mutableStateOf(false) }

    // Percentage Coupon values
    val percentageValue = remember { mutableStateOf("") }
    val percentageSelected = remember { mutableStateOf(false) }

    // Category Discount values
    val categoryValue = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf(ProductCategory.CLOTHING) }
    val categoryDiscountSelected = remember { mutableStateOf(false) }

    // Points values
    val pointsValue = remember { mutableStateOf("") }
    val pointsSelected = remember { mutableStateOf(false) }

    // Bulk Discount values
    val bulkEveryValue = remember { mutableStateOf("") }
    val bulkDiscountValue = remember { mutableStateOf("") }
    val bulkDiscountSelected = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Apply Discounts",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        Text(
            text = "Cart Total: ${String.format("%.2f", cartTotal)} THB",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Fixed Amount Coupon
            item {
                CampaignCard(
                    title = "Fixed Amount Coupon",
                    description = "Subtract a fixed amount from total",
                    isSelected = fixedAmountSelected.value,
                    onSelectedChange = {
                        fixedAmountSelected.value = it
                        percentageSelected.value = false
                    }
                ) {
                    if (fixedAmountSelected.value) {
                        OutlinedTextField(
                            value = fixedAmountValue.value,
                            onValueChange = { fixedAmountValue.value = it },
                            label = { Text("Amount (THB)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            singleLine = true
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Percentage Coupon
            item {
                CampaignCard(
                    title = "Percentage Coupon",
                    description = "Discount entire cart by percentage",
                    isSelected = percentageSelected.value,
                    onSelectedChange = {
                        percentageSelected.value = it
                        fixedAmountSelected.value = false
                    }
                ) {
                    if (percentageSelected.value) {
                        OutlinedTextField(
                            value = percentageValue.value,
                            onValueChange = { percentageValue.value = it },
                            label = { Text("Percentage (%)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            singleLine = true
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Category Percentage Discount
            item {
                CampaignCard(
                    title = "Category Discount",
                    description = "Discount specific product category",
                    isSelected = categoryDiscountSelected.value,
                    onSelectedChange = { categoryDiscountSelected.value = it }
                ) {
                    if (categoryDiscountSelected.value) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Category:", modifier = Modifier.width(80.dp))
                                ProductCategory.values().forEach { category ->
                                    Button(
                                        onClick = { selectedCategory.value = category },
                                        modifier = Modifier.weight(1f),
                                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                            containerColor = if (selectedCategory.value == category)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    ) {
                                        Text(category.name.take(3), fontSize = 10.sp)
                                    }
                                }
                            }
                            OutlinedTextField(
                                value = categoryValue.value,
                                onValueChange = { categoryValue.value = it },
                                label = { Text("Percentage (%)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                singleLine = true
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Points Discount
            item {
                CampaignCard(
                    title = "Points Discount",
                    description = "Use points for discount (1 point = 1 THB, max 20%)",
                    isSelected = pointsSelected.value,
                    onSelectedChange = { pointsSelected.value = it }
                ) {
                    if (pointsSelected.value) {
                        OutlinedTextField(
                            value = pointsValue.value,
                            onValueChange = { pointsValue.value = it },
                            label = { Text("Points") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            singleLine = true
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Bulk Discount (Seasonal)
            item {
                CampaignCard(
                    title = "Seasonal Discount",
                    description = "Discount Y THB for every X THB",
                    isSelected = bulkDiscountSelected.value,
                    onSelectedChange = { bulkDiscountSelected.value = it }
                ) {
                    if (bulkDiscountSelected.value) {
                        OutlinedTextField(
                            value = bulkEveryValue.value,
                            onValueChange = { bulkEveryValue.value = it },
                            label = { Text("Every (THB)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = bulkDiscountValue.value,
                            onValueChange = { bulkDiscountValue.value = it },
                            label = { Text("Discount Amount (THB)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            singleLine = true
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Show Result if calculated
        if (discountResult != null) {
            Divider()
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Discount Summary",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    discountResult.discountBreakdown.forEach { breakdown ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = breakdown.campaignName,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "-${String.format("%.2f", breakdown.discountAmount)} THB",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Final Price:",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${String.format("%.2f", discountResult.finalPrice)} THB",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = "You saved ${String.format("%.2f", discountResult.totalDiscount)} THB!",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Apply Button
        Button(
            onClick = {
                val campaigns = mutableListOf<Campaign>()

                if (fixedAmountSelected.value && fixedAmountValue.value.isNotEmpty()) {
                    try {
                        campaigns.add(
                            Campaign.FixedAmountCoupon(
                                id = "fixed_${System.currentTimeMillis()}",
                                name = "Fixed Coupon (${fixedAmountValue.value} THB)",
                                amount = fixedAmountValue.value.toDouble()
                            )
                        )
                    } catch (e: Exception) {
                        // Invalid input
                    }
                }

                if (percentageSelected.value && percentageValue.value.isNotEmpty()) {
                    try {
                        campaigns.add(
                            Campaign.PercentageCoupon(
                                id = "percent_${System.currentTimeMillis()}",
                                name = "Percentage Coupon (${percentageValue.value}%)",
                                percentage = percentageValue.value.toDouble()
                            )
                        )
                    } catch (e: Exception) {
                        // Invalid input
                    }
                }

                if (categoryDiscountSelected.value && categoryValue.value.isNotEmpty()) {
                    try {
                        campaigns.add(
                            Campaign.CategoryPercentageDiscount(
                                id = "cat_${System.currentTimeMillis()}",
                                name = "${selectedCategory.value.name} Discount (${categoryValue.value}%)",
                                category = selectedCategory.value,
                                percentage = categoryValue.value.toDouble()
                            )
                        )
                    } catch (e: Exception) {
                        // Invalid input
                    }
                }

                if (pointsSelected.value && pointsValue.value.isNotEmpty()) {
                    try {
                        campaigns.add(
                            Campaign.PointsDiscount(
                                id = "points_${System.currentTimeMillis()}",
                                name = "Points Discount (${pointsValue.value} pts)",
                                points = pointsValue.value.toInt()
                            )
                        )
                    } catch (e: Exception) {
                        // Invalid input
                    }
                }

                if (bulkDiscountSelected.value && bulkEveryValue.value.isNotEmpty() && bulkDiscountValue.value.isNotEmpty()) {
                    try {
                        campaigns.add(
                            Campaign.BulkDiscount(
                                id = "bulk_${System.currentTimeMillis()}",
                                name = "Bulk Discount (${bulkDiscountValue.value} THB off per ${bulkEveryValue.value} THB)",
                                everyAmount = bulkEveryValue.value.toDouble(),
                                discountAmount = bulkDiscountValue.value.toDouble()
                            )
                        )
                    } catch (e: Exception) {
                        // Invalid input
                    }
                }

                onApplyDiscounts(campaigns)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Calculate Discount")
        }
    }
}

@Composable
fun CampaignCard(
    title: String,
    description: String,
    isSelected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                Color(0xFFE3F2FD)
            else
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = onSelectedChange
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            content()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 600)
@Composable
fun CampaignCardPreview_Unselected() {
    DiscountModuleTheme {
        CampaignCard(
            title = "Fixed Amount Coupon",
            description = "Subtract a fixed amount from total",
            isSelected = false,
            onSelectedChange = { }
        ) {
            // Empty content when not selected
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 700)
@Composable
fun CampaignCardPreview_Selected() {
    DiscountModuleTheme {
        CampaignCard(
            title = "Percentage Coupon",
            description = "Discount entire cart by percentage",
            isSelected = true,
            onSelectedChange = { }
        ) {
            OutlinedTextField(
                value = "15",
                onValueChange = { },
                label = { Text("Percentage (%)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 900)
@Composable
fun DiscountSelectionScreenPreview_NoResult() {
    DiscountModuleTheme {
        DiscountSelectionScreen(
            cartTotal = 1200.0,
            discountResult = null,
            onApplyDiscounts = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 1000)
@Composable
fun DiscountSelectionScreenPreview_WithResult() {
    DiscountModuleTheme {
        DiscountSelectionScreen(
            cartTotal = 1200.0,
            discountResult = DiscountResult(
                originalPrice = 1200.0,
                discountBreakdown = listOf(
                    DiscountBreakdown(
                        campaignName = "Fixed Coupon (100 THB)",
                        discountAmount = 100.0,
                        order = 1
                    ),
                    DiscountBreakdown(
                        campaignName = "Percentage Coupon (10%)",
                        discountAmount = 110.0,
                        order = 1
                    )
                ),
                totalDiscount = 210.0,
                finalPrice = 990.0,
                appliedCampaigns = emptyList()
            ),
            onApplyDiscounts = { },
            onNavigateBack = { }
        )
    }
}
