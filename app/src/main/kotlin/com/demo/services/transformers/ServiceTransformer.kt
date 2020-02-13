package com.demo.services.transformers

import com.demo.dao.ServiceDao
import com.demo.dto.ServiceDto
import com.demo.services.model.Transformer
import com.demo.services.utils.getUUID
import com.demo.services.utils.parseForDoubleUUID
import java.util.*

object ServiceTransformer: Transformer<ServiceDao, ServiceDto> {
    override fun getFromCsv(csv: ByteArray): List<ServiceDao> {
        return parseForDoubleUUID(csv).map {
            ServiceDao(getUUID(it[0]), getUUID(it[1]), it[2], it[3].toFloat(), it[4].toInt())
        }
    }

    override fun getFromDto(dto: ServiceDto, id: UUID): ServiceDao {
        return ServiceDao(
            id,
            getUUID(dto.appointmentId),
            dto.name,
            dto.price,
            dto.loyaltyPoint
        )
    }
}