package com.demo.services.utils

import org.joda.time.DateTime

fun isUUID(id: String): Boolean {
    val uuidPattern = "\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b"
    return uuidPattern.toRegex().matches(id)
}

fun isEmail(email: String): Boolean {
    val emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
    return emailPattern.toRegex().matches(email)
}

fun isPhoneNumber(phone: String): Boolean {
    val phonePattern = "^(\\(\\d{3}\\)|\\d+)([\\s-.]\\d+)+$"
    return phonePattern.toRegex().matches(phone)
}

fun hasProperLength(field: String): Boolean {
    return field.isNotBlank() && field.length > 2
}

fun isPriceDefined(price: Float): Boolean {
    return price >= 1.0
}

fun isLoyaltyDefined(loyalty: Int): Boolean {
    return loyalty >= 1
}

fun isGender(gender: String): Boolean {
    return gender == "Male" || gender == "Female"
}

fun isProperDateOrder(from: DateTime, to: DateTime): Boolean {
    return to.isAfter(from)
}