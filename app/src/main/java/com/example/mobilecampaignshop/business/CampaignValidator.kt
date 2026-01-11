package com.example.mobilecampaignshop.business


import com.example.mobilecampaignshop.models.Campaign
import com.example.mobilecampaignshop.models.CampaignType
import com.example.mobilecampaignshop.models.getCampaignType

class CampaignValidator {

    fun validateCampaignCombination(campaigns: List<Campaign>): ValidationResult {
        if (campaigns.isEmpty()) {
            return ValidationResult(true, "No campaigns to validate")
        }

        val couponCampaigns = campaigns.filter { it.getCampaignType() == CampaignType.COUPON }
        if (couponCampaigns.size > 1) {
            return ValidationResult(
                false,
                "Cannot apply multiple coupon campaigns."
            )
        }

        return ValidationResult(true, "All campaigns are valid")
    }

    fun validateCampaignValues(campaigns: List<Campaign>): ValidationResult {
        for (campaign in campaigns) {
            when (campaign) {
                is Campaign.FixedAmountCoupon -> {
                    if (campaign.amount < 0) {
                        return ValidationResult(false, "Amount cannot be negative")
                    }
                }
                is Campaign.PercentageCoupon -> {
                    if (campaign.percentage < 0 || campaign.percentage > 100) {
                        return ValidationResult(false, "Percentage must be 0-100")
                    }
                }
                is Campaign.CategoryPercentageDiscount -> {
                    if (campaign.percentage < 0 || campaign.percentage > 100) {
                        return ValidationResult(false, "Percentage must be 0-100")
                    }
                }
                is Campaign.PointsDiscount -> {
                    if (campaign.points < 0) {
                        return ValidationResult(false, "Points cannot be negative")
                    }
                }
                is Campaign.BulkDiscount -> {
                    if (campaign.everyAmount <= 0 || campaign.discountAmount < 0) {
                        return ValidationResult(false, "Invalid discount values")
                    }
                }
            }
        }
        return ValidationResult(true, "All values valid")
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val message: String
)
