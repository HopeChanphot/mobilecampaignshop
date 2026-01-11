package com.example.mobilecampaignshop

import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilecampaignshop.ui.theme.DiscountModuleTheme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mobilecampaignshop.models.Product
import com.example.mobilecampaignshop.models.ProductCategory
import com.example.mobilecampaignshop.ui.screens.CartScreen
import com.example.mobilecampaignshop.ui.screens.DiscountSelectionScreen
import com.example.mobilecampaignshop.ui.screens.ProductListScreen
import com.example.mobilecampaignshop.ui.theme.DiscountModuleTheme
import com.example.mobilecampaignshop.viewmodel.DiscountViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscountModuleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = remember { DiscountViewModel() }
                    val currentScreen = remember { mutableStateOf("products") }

                    when (currentScreen.value) {
                        "products" -> {
                            ProductListScreen(
                                products = sampleProducts,
                                cartItemCount = viewModel.cart.items.size,
                                onAddToCart = { product ->
                                    viewModel.addToCart(product)
                                },
                                onNavigateToCart = {
                                    currentScreen.value = "cart"
                                }
                            )
                        }
                        "cart" -> {
                            CartScreen(
                                cartItems = viewModel.cart.items,
                                totalPrice = viewModel.cart.getTotalPrice(),
                                onQuantityChange = { cartItem, newQuantity ->
                                    viewModel.updateItemQuantity(cartItem, newQuantity)
                                },
                                onRemoveItem = { cartItem ->
                                    viewModel.removeFromCart(cartItem)
                                },
                                onNavigateToDiscount = {
                                    currentScreen.value = "discount"
                                },
                                onNavigateBack = {
                                    currentScreen.value = "products"
                                }
                            )
                        }
                        "discount" -> {
                            DiscountSelectionScreen(
                                cartTotal = viewModel.cart.getTotalPrice(),
                                discountResult = viewModel.discountResult,
                                onApplyDiscounts = { campaigns ->
                                    viewModel.applyDiscounts(campaigns)
                                },
                                onNavigateBack = {
                                    currentScreen.value = "cart"
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, heightDp = 800)
    @Composable
    fun MainActivityPreview() {
        DiscountModuleTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ProductListScreen(
                    products = sampleProducts.take(5),
                    cartItemCount = 0,
                    onAddToCart = {},
                    onNavigateToCart = {}
                )
            }
        }
    }






}

// Sample products data
private val sampleProducts = listOf(
    // Clothing (4 items)
    Product("1", "T-Shirt", 350.0, ProductCategory.CLOTHING),
    Product("2", "Hoodie", 700.0, ProductCategory.CLOTHING),
    Product("3", "Jeans", 1200.0, ProductCategory.CLOTHING),
    Product("4", "Shorts", 450.0, ProductCategory.CLOTHING),

    // Accessories (5 items)
    Product("5", "Hat", 250.0, ProductCategory.ACCESSORIES),
    Product("6", "Watch", 850.0, ProductCategory.ACCESSORIES),
    Product("7", "Bag", 640.0, ProductCategory.ACCESSORIES),
    Product("8", "Belt", 230.0, ProductCategory.ACCESSORIES),
    Product("9", "Scarf", 320.0, ProductCategory.ACCESSORIES),

    // Electronics (5 items)
    Product("10", "Headphones", 1500.0, ProductCategory.ELECTRONICS),
    Product("11", "Phone Case", 350.0, ProductCategory.ELECTRONICS),
    Product("12", "USB Cable", 180.0, ProductCategory.ELECTRONICS),
    Product("13", "Power Bank", 950.0, ProductCategory.ELECTRONICS),
    Product("14", "Wireless Charger", 800.0, ProductCategory.ELECTRONICS),
)

