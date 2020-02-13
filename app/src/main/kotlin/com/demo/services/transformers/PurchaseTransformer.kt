package com.demo.services.transformers

import com.demo.dao.PurchaseDao
import com.demo.dto.PurchaseDto
import com.demo.services.model.Transformer
import com.demo.services.utils.getUUID
import com.demo.services.utils.parseForDoubleUUID
import java.util.*

object PurchaseTransformer: Transformer<PurchaseDao, PurchaseDto> {
    override fun getFromCsv(csv: ByteArray): List<PurchaseDao> {
        return parseForDoubleUUID(csv).map {
            PurchaseDao(getUUID(it[0]), getUUID(it[1]), it[2], it[3].toFloat(), it[4].toInt())
        }
    }

    override fun getFromDto(dto: PurchaseDto, id: UUID): PurchaseDao {
        return PurchaseDao(
            id,
            getUUID(dto.appointmentId),
            dto.name,
            dto.price,
            dto.loyaltyPoint
        )
    }
}