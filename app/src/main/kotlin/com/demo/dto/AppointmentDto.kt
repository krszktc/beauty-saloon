package com.demo.dto

import org.joda.time.DateTime
import java.util.*

data class AppointmentDto (
    val clientId: String,
    val startTime: DateTime,
    val endTime: DateTime
)


