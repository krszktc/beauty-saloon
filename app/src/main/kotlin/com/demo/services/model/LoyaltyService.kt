package com.demo.services.model

interface LoyaltyService<T> {
    fun getLoyalty(count: Int, date: String): List<T>
}