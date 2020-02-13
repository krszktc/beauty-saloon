package com.demo.dto.serializers

import com.demo.dao.PurchaseDao
import com.demo.dto.PurchaseDto
import com.demo.dto.model.Serializer
import com.google.gson.Gson

object PurchaseSerializer: Serializer<PurchaseDao, PurchaseDto> {
    override fun serialize(dao: PurchaseDao?): String {
        return Gson().toJson(dao)
    }

    override fun deserialize(json: String): PurchaseDto {
        return Gson().fromJson(json, PurchaseDto::class.java)
    }

}