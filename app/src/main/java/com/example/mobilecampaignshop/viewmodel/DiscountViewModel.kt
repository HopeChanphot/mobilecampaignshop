package com.example.mobilecampaignshop.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobilecampaignshop.business.CampaignValidator
import com.example.mobilecampaignshop.business.DiscountCalculator
import com.example.mobilecampaignshop.models.Campaign
import com.example.mobilecampaignshop.models.Cart
import com.example.mobilecampaignshop.models.CartItem
import com.example.mobilecampaignshop.models.DiscountResult
import com.example.mobilecampaignshop.models.Product
import com.example.mobilecampaignshop.models.ProductCategory

class DiscountViewModel : ViewModel() {
    private val discountCalculator = DiscountCalculator()
    private val campaignValidator = CampaignValidator()

    private val _cart = mutableStateOf(Cart())
    val cart: Cart
        get() = _cart.value

    private val _discountResult = mutableStateOf<DiscountResult?>(null)
    val discountResult: DiscountResult?
        get() = _discountResult.value

    private val _validationError = mutableStateOf<String?>(null)
    val validationError: String?
        get() = _validationError.value

    fun addToCart(product: Product) {
        val currentItems = _cart.value.items.toMutableList()
        val existingItem = currentItems.find { it.product.id == product.id }

        if (existingItem != null) {
            val index = currentItems.indexOf(existingItem)
            currentItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            currentItems.add(CartItem(product, 1))
        }

        _cart.value = _cart.value.copy(items = currentItems)
    }

    fun updateItemQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
            return
        }

        val currentItems = _cart.value.items.toMutableList()
        val index = currentItems.indexOf(cartItem)
        if (index >= 0) {
            currentItems[index] = cartItem.copy(quantity = newQuantity)
            _cart.value = _cart.value.copy(items = currentItems)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        val currentItems = _cart.value.items.toMutableList()
        currentItems.remove(cartItem)
        _cart.value = _cart.value.copy(items = currentItems)
    }

    fun clearCart() {
        _cart.value = Cart()
        _discountResult.value = null
        _validationError.value = null
    }

    fun applyDiscounts(campaigns: List<Campaign>) {
        _validationError.value = null

        // Validate combination
        val combinationResult = campaignValidator.validateCampaignCombination(campaigns)
        if (!combinationResult.isValid) {
            _validationError.value = combinationResult.message
            return
        }

        // Validate values
        val valuesResult = campaignValidator.validateCampaignValues(campaigns)
        if (!valuesResult.isValid) {
            _validationError.value = valuesResult.message
            return
        }

        // Calculate discount
        val result = discountCalculator.calculateFinalPrice(_cart.value, campaigns)
        _discountResult.value = result
    }

    fun completeOrder() {
        // Here you would send the order to backend
        // For now, just clear and reset
        clearCart()
    }
}
