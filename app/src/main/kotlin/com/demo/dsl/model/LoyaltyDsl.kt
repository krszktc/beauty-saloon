package com.demo.dsl.model

import org.joda.time.DateTime

interface LoyaltyDsl<T> {
    fun getLoyalty(count: Int, date: DateTime): List<T>
}