package com.example.mobilecampaignshop.models

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val category: ProductCategory,
    val imageResId: Int = 0
)

enum class ProductCategory {
    CLOTHING,
    ACCESSORIES,
    ELECTRONICS
}
