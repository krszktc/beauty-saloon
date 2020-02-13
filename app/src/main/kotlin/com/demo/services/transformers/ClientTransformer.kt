package com.demo.services.transformers

import com.demo.dao.ClientDao
import com.demo.dto.ClientDto
import com.demo.services.model.Transformer
import com.demo.services.utils.getUUID
import com.demo.services.utils.parseForSingleUUID
import java.util.*

object ClientTransformer: Transformer<ClientDao, ClientDto> {
    override fun getFromCsv(csv: ByteArray): List<ClientDao> {
        return parseForSingleUUID(csv).map {
            ClientDao(getUUID(it[0]), it[1], it[2], it[3], it[4], it[5], it[6].toBoolean())
        }
    }

    override fun getFromDto(dto: ClientDto, id: UUID): ClientDao {
        return  ClientDao(
            id,
            dto.firstName,
            dto.lastName,
            dto.email,
            dto.phone,
            dto.gender,
            false)
    }
}