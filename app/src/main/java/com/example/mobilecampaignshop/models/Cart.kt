package com.example.mobilecampaignshop.models

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class Cart(
    val items: MutableList<CartItem> = mutableListOf()
) {
    fun getTotalPrice(): Double {
        return items.sumOf { it.product.price * it.quantity }
    }

    fun getItemsByCategory(category: ProductCategory): List<CartItem> {
        return items.filter { it.product.category == category }
    }

    fun getCategoryTotal(category: ProductCategory): Double {
        return getItemsByCategory(category).sumOf { it.product.price * it.quantity }
    }
}
