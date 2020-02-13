package com.demo.dto.adapter

import com.demo.services.utils.parseDateTime
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import org.joda.time.DateTime
import java.lang.reflect.Type

class DateTimeOut: JsonSerializer<DateTime> {
    override fun serialize(dateTime: DateTime?, type: Type?, context: JsonSerializationContext?): JsonElement {
        val dateTimeString = parseDateTime(dateTime)
        return JsonPrimitive(dateTimeString)
    }
}