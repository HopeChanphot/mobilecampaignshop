package com.example.mobilecampaignshop.business


import com.example.mobilecampaignshop.models.Cart
import com.example.mobilecampaignshop.models.Campaign
import com.example.mobilecampaignshop.models.CampaignType
import com.example.mobilecampaignshop.models.DiscountResult
import com.example.mobilecampaignshop.models.DiscountBreakdown
import com.example.mobilecampaignshop.models.getCampaignType
import kotlin.math.floor

class DiscountCalculator {

    fun calculateFinalPrice(
        cart: Cart,
        campaigns: List<Campaign>
    ): DiscountResult {
        if (cart.items.isEmpty()) {
            return DiscountResult(
                originalPrice = 0.0,
                discountBreakdown = emptyList(),
                totalDiscount = 0.0,
                finalPrice = 0.0,
                appliedCampaigns = emptyList()
            )
        }

        val originalPrice = cart.getTotalPrice()
        var currentPrice = originalPrice
        val breakdowns = mutableListOf<DiscountBreakdown>()

        val sortedCampaigns = campaigns.sortedBy {
            when (it.getCampaignType()) {
                CampaignType.COUPON -> 1
                CampaignType.ON_TOP -> 2
                CampaignType.SEASONAL -> 3
            }
        }

        for (campaign in sortedCampaigns) {
            val discountAmount = calculateDiscountAmount(campaign, cart, currentPrice)
            if (discountAmount > 0) {
                currentPrice -= discountAmount
                breakdowns.add(
                    DiscountBreakdown(
                        campaignName = campaign.name,
                        discountAmount = discountAmount,
                        order = when (campaign.getCampaignType()) {
                            CampaignType.COUPON -> 1
                            CampaignType.ON_TOP -> 2
                            CampaignType.SEASONAL -> 3
                        }
                    )
                )
            }
        }

        currentPrice = maxOf(0.0, currentPrice)
        val totalDiscount = originalPrice - currentPrice

        return DiscountResult(
            originalPrice = originalPrice,
            discountBreakdown = breakdowns.sortedBy { it.order },
            totalDiscount = totalDiscount,
            finalPrice = currentPrice,
            appliedCampaigns = sortedCampaigns
        )
    }

    private fun calculateDiscountAmount(
        campaign: Campaign,
        cart: Cart,
        currentPrice: Double
    ): Double {
        return when (campaign) {
            is Campaign.FixedAmountCoupon -> {
                minOf(campaign.amount, currentPrice)
            }
            is Campaign.PercentageCoupon -> {
                currentPrice * (campaign.percentage / 100.0)
            }
            is Campaign.CategoryPercentageDiscount -> {
                val categoryTotal = cart.getCategoryTotal(campaign.category)
                categoryTotal * (campaign.percentage / 100.0)
            }
            is Campaign.PointsDiscount -> {
                val discountFromPoints = campaign.points.toDouble()
                val maxDiscount = currentPrice * 0.20
                minOf(discountFromPoints, maxDiscount)
            }
            is Campaign.BulkDiscount -> {
                val timesApplicable = floor(currentPrice / campaign.everyAmount).toInt()
                timesApplicable * campaign.discountAmount
            }
        }
    }
}
