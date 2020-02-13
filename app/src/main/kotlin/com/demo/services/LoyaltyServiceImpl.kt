package com.demo.services

import com.demo.dsl.LoyaltyReportDsl
import com.demo.dto.LoyaltyDto
import com.demo.services.model.LoyaltyService
import com.demo.services.utils.logger
import org.joda.time.DateTime

class LoyaltyServiceImpl (
    private val dslService: LoyaltyReportDsl
): LoyaltyService<LoyaltyDto> {

    private val logger = logger<LoyaltyServiceImpl>()

    override fun getLoyalty(count: Int, date: String): List<LoyaltyDto> {
        logger.info("Service to get Loyalty report")
        val dateTime = DateTime.parse(date)
        return dslService.getLoyalty(count, dateTime)
    }
}