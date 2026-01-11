package com.example.mobilecampaignshop.ui.screens

import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilecampaignshop.ui.theme.DiscountModuleTheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilecampaignshop.models.CartItem
import com.example.mobilecampaignshop.models.Product
import com.example.mobilecampaignshop.models.ProductCategory
import com.example.mobilecampaignshop.ui.components.CartItemCard
import com.example.mobilecampaignshop.ui.components.ProductCard

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    totalPrice: Double,
    onQuantityChange: (CartItem, Int) -> Unit,
    onRemoveItem: (CartItem) -> Unit,
    onNavigateToDiscount: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Shopping Cart",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }

        if (cartItems.isEmpty()) {
            // Empty Cart State
            Text(
                text = "Your cart is empty",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(32.dp)
            )
        } else {
            // Cart Items
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onQuantityChange = { newQuantity ->
                            onQuantityChange(cartItem, newQuantity)
                        },
                        onRemove = { onRemoveItem(cartItem) },
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Price Summary
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${String.format("%.2f", totalPrice)} THB",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Apply Discount Button
            Button(
                onClick = onNavigateToDiscount,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Apply Discounts")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 800)
@Composable
fun CartScreenPreview_WithItems() {
    DiscountModuleTheme {
        CartScreen(
            cartItems = listOf(
                CartItem(
                    product = Product("1", "T-Shirt", 350.0, ProductCategory.CLOTHING),
                    quantity = 2
                ),
                CartItem(
                    product = Product("5", "Hat", 250.0, ProductCategory.ACCESSORIES),
                    quantity = 1
                ),
                CartItem(
                    product = Product("10", "Headphones", 1500.0, ProductCategory.ELECTRONICS),
                    quantity = 1
                )
            ),
            totalPrice = 2650.0,
            onQuantityChange = { _, _ -> },
            onRemoveItem = { },
            onNavigateToDiscount = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 600)
@Composable
fun CartScreenPreview_Empty() {
    DiscountModuleTheme {
        CartScreen(
            cartItems = emptyList(),
            totalPrice = 0.0,
            onQuantityChange = { _, _ -> },
            onRemoveItem = { },
            onNavigateToDiscount = { },
            onNavigateBack = { }
        )
    }
}
