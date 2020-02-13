package com.demo.dto.serializers

import com.demo.dao.ServiceDao
import com.demo.dto.ServiceDto
import com.demo.dto.model.Serializer
import com.google.gson.Gson

object ServiceSerializer: Serializer<ServiceDao, ServiceDto> {
    override fun serialize(dao: ServiceDao?): String {
        return Gson().toJson(dao)
    }

    override fun deserialize(json: String): ServiceDto {
        return Gson().fromJson(json, ServiceDto::class.java)
    }
}