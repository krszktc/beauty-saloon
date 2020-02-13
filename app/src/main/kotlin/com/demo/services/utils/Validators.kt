package com.demo.services.utils

import com.demo.dto.AppointmentDto
import com.demo.dto.ClientDto
import com.demo.dto.PurchaseDto
import com.demo.dto.ServiceDto

val appointmentValidator: (AppointmentDto) -> String = {
    when {
        !isUUID(it.clientId) -> "ClientId is not UUID"
        !isProperDateOrder(it.startTime, it.endTime) -> "End date/time must be greater"
        else -> ""
    }
}

val clientValidator: (ClientDto) -> String = {
    when {
        !hasProperLength(it.firstName) -> "FirstName hast to be longer than 2 character"
        !hasProperLength(it.lastName) -> "LastName hast to be longer than 2 character"
        !isEmail(it.email) -> "Wrong email format"
        !isPhoneNumber(it.phone)-> "Wrong prone format"
        !isGender(it.gender) -> "Gender could be 'Male' or 'Female'"
        else -> ""
    }
}

val purchaseValidator: (PurchaseDto) -> String = {
    when {
        !isUUID(it.appointmentId) -> "AppointmentId is not UUID"
        !hasProperLength(it.name) -> "Name has to be longer than 2 character"
        !isPriceDefined(it.price) -> "Price has to be set"
        !isLoyaltyDefined(it.loyaltyPoint) -> "Loyalty has to be set"
        else -> ""
    }
}

val serviceValidator: (ServiceDto) -> String = {
    when {
        !isUUID(it.appointmentId) -> "ServiceId is not UUID"
        !hasProperLength(it.name) -> "Name has to be longer than 2 character"
        !isPriceDefined(it.price) -> "Price has to be set"
        !isLoyaltyDefined(it.loyaltyPoint) -> "Loyalty has to be set"
        else -> ""
    }
}