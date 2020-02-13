package com.demo.dsl

import com.demo.dao.Appointments
import com.demo.dao.Clients
import com.demo.dao.Purchases
import com.demo.dao.Services
import com.demo.dsl.model.LoyaltyDsl
import com.demo.dto.LoyaltyDto
import com.demo.services.utils.logger
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

object LoyaltyReportDsl: LoyaltyDsl<LoyaltyDto> {

    private val logger = logger<LoyaltyReportDsl>()

    override fun getLoyalty(count: Int, date: DateTime): List<LoyaltyDto> {
        logger.info("Execute query to get Loyalty report")
        val clientId = Clients.id
        val loyaltyPointSum = Expression.build {
            Services.loyaltyPoint.sum() + Purchases.loyaltyPoint.sum()
        }
        return transaction {
            addLogger(StdOutSqlLogger)
            (Clients innerJoin Appointments leftJoin Services leftJoin Purchases)
                .slice(
                    clientId,
                    loyaltyPointSum
                )
                .select {
                    not(Clients.banned) and
                    Appointments.startTime.greaterEq(date)
                }
                .groupBy(clientId)
                .orderBy(loyaltyPointSum, SortOrder.DESC)
                .limit(count)
                .map {
                    val sum = it[loyaltyPointSum] ?: 0
                    LoyaltyDto(it[clientId].toString(), sum)
                }
        }
    }

}