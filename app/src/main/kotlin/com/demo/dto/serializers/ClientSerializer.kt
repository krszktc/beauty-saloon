package com.demo.dto.serializers

import com.demo.dao.ClientDao
import com.demo.dto.ClientDto
import com.demo.dto.model.Serializer
import com.google.gson.Gson

object ClientSerializer: Serializer<ClientDao, ClientDto> {
    override fun serialize(dao: ClientDao?): String {
        return Gson().toJson(dao)
    }

    override fun deserialize(json: String): ClientDto {
        return Gson().fromJson(json, ClientDto::class.java)
    }

}