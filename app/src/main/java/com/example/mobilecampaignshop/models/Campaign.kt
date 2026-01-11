package com.example.mobilecampaignshop.models


sealed class Campaign {
    abstract val id: String
    abstract val name: String

    data class FixedAmountCoupon(
        override val id: String,
        override val name: String,
        val amount: Double
    ) : Campaign()

    data class PercentageCoupon(
        override val id: String,
        override val name: String,
        val percentage: Double
    ) : Campaign()

    data class CategoryPercentageDiscount(
        override val id: String,
        override val name: String,
        val category: ProductCategory,
        val percentage: Double
    ) : Campaign()

    data class PointsDiscount(
        override val id: String,
        override val name: String,
        val points: Int
    ) : Campaign()

    data class BulkDiscount(
        override val id: String,
        override val name: String,
        val everyAmount: Double,
        val discountAmount: Double
    ) : Campaign()
}

enum class CampaignType {
    COUPON,
    ON_TOP,
    SEASONAL
}

fun Campaign.getCampaignType(): CampaignType {
    return when (this) {
        is Campaign.FixedAmountCoupon -> CampaignType.COUPON
        is Campaign.PercentageCoupon -> CampaignType.COUPON
        is Campaign.CategoryPercentageDiscount -> CampaignType.ON_TOP
        is Campaign.PointsDiscount -> CampaignType.ON_TOP
        is Campaign.BulkDiscount -> CampaignType.SEASONAL
    }
}
