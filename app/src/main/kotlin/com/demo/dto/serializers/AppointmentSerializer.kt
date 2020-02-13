package com.demo.dto.serializers

import com.demo.dao.AppointmentDao
import com.demo.dto.AppointmentDto
import com.demo.dto.adapter.DateTimeIn
import com.demo.dto.adapter.DateTimeOut
import com.demo.dto.model.Serializer
import com.google.gson.GsonBuilder
import org.joda.time.DateTime

object AppointmentSerializer: Serializer<AppointmentDao, AppointmentDto> {
    override fun serialize(dao: AppointmentDao?): String {
        val builder = GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, DateTimeOut())
            .create()

        return builder.toJson(dao)
    }

    override fun deserialize(json: String): AppointmentDto {
        val builder = GsonBuilder()
            .registerTypeAdapter(DateTime::class.java, DateTimeIn())
            .create()

        return builder.fromJson(json, AppointmentDto::class.java)
    }

}