package com.example.mobilecampaignshop.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilecampaignshop.models.Product
import com.example.mobilecampaignshop.models.ProductCategory
import com.example.mobilecampaignshop.ui.components.ProductCard
import com.example.mobilecampaignshop.ui.theme.DiscountModuleTheme

@Composable
fun ProductListScreen(
    products: List<Product>,
    cartItemCount: Int,
    onAddToCart: (Product) -> Unit,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Store",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Subtitle
        Text(
            text = "Select products to add to your cart",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Products List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onAddToCart = onAddToCart,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Go to Cart Button
        Button(
            onClick = onNavigateToCart,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = cartItemCount > 0
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Cart",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Go to Cart ($cartItemCount items)")
        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 800)
@Composable
fun ProductListScreenPreview_WithItems() {
    DiscountModuleTheme {
        ProductListScreen(
            products = listOf(
                Product("1", "T-Shirt", 350.0, ProductCategory.CLOTHING),
                Product("2", "Hoodie", 700.0, ProductCategory.CLOTHING),
                Product("5", "Hat", 250.0, ProductCategory.ACCESSORIES),
                Product("10", "Headphones", 1500.0, ProductCategory.ELECTRONICS),
            ),
            cartItemCount = 2,
            onAddToCart = {},
            onNavigateToCart = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 600)
@Composable
fun ProductListScreenPreview_EmptyCart() {
    DiscountModuleTheme {
        ProductListScreen(
            products = listOf(
                Product("1", "T-Shirt", 350.0, ProductCategory.CLOTHING),
                Product("2", "Hoodie", 700.0, ProductCategory.CLOTHING),
            ),
            cartItemCount = 0,
            onAddToCart = {},
            onNavigateToCart = {}
        )
    }
}