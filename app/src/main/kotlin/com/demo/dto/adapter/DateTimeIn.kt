package com.demo.dto.adapter

import com.demo.services.utils.getDateTime
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import org.joda.time.DateTime
import java.lang.reflect.Type

class DateTimeIn: JsonDeserializer<DateTime> {
    override fun deserialize(json: JsonElement?, type: Type?, context: JsonDeserializationContext?): DateTime {
        val timeString = json?.asString ?: throw JsonParseException("DateTime is missing")
        return getDateTime(timeString)
    }
}