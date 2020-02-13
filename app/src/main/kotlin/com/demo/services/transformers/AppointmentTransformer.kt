package com.demo.services.transformers

import com.demo.dao.AppointmentDao
import com.demo.dto.AppointmentDto
import com.demo.services.model.Transformer
import com.demo.services.utils.getDateTime
import com.demo.services.utils.getUUID
import com.demo.services.utils.parseForDoubleUUID
import java.util.*

object AppointmentTransformer: Transformer<AppointmentDao, AppointmentDto> {
    override fun getFromCsv(csv: ByteArray): List<AppointmentDao> {
        return parseForDoubleUUID(csv).map {
            AppointmentDao(getUUID(it[0]), getUUID(it[1]), getDateTime(it[2]), getDateTime(it[3]))
        }
    }

    override fun getFromDto(dto: AppointmentDto, id: UUID): AppointmentDao {
        return AppointmentDao(
            id,
            getUUID(dto.clientId),
            dto.startTime,
            dto.endTime)
    }
}
