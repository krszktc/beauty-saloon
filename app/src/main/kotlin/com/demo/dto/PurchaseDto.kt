package com.demo.dto

data class PurchaseDto (
    val appointmentId: String,
    val name: String,
    val price: Float,
    val loyaltyPoint: Int
)