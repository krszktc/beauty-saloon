package com.demo.services

import com.demo.dsl.model.BanDsl
import com.demo.dto.BanDto
import com.demo.services.model.BanService
import com.demo.services.model.StatusException
import com.demo.services.utils.logger
import java.util.*

class BanServiceImpl(
    private val BanDsl: BanDsl
): BanService {

    private val logger = logger<BanServiceImpl>()

    override fun setBanned(dto: BanDto) {
        logger.info("Service to Ban Client")
        val id = UUID.fromString(dto.id)
        val status = BanDsl.setBanned(id, dto.status)

        if(status == 0) {
            throw StatusException("Id doesn't exist")
        }
    }
}