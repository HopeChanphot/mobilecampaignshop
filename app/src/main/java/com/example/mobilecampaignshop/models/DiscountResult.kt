package com.example.mobilecampaignshop.models


data class DiscountResult(
    val originalPrice: Double,
    val discountBreakdown: List<DiscountBreakdown>,
    val totalDiscount: Double,
    val finalPrice: Double,
    val appliedCampaigns: List<Campaign>
)

data class DiscountBreakdown(
    val campaignName: String,
    val discountAmount: Double,
    val order: Int
)
