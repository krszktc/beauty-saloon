package com.demo.dsl

import com.demo.dao.ServiceDao
import com.demo.dao.Services
import com.demo.dsl.model.CrudDsl
import com.demo.services.utils.logger
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object ServiceCrudDsl: CrudDsl<ServiceDao> {

    private val logger = logger<ServiceCrudDsl>()

    override fun getAll(): List<ServiceDao> {
        return transaction {
            addLogger(StdOutSqlLogger)
            Services.selectAll()
                .map { getDto(it) }
        }
    }

    override fun create(dto: ServiceDao): UUID {
        logger.info("Execute query to create Service")
        return transaction {
            addLogger(StdOutSqlLogger)
            Services.insert {
                it[id] = dto.id
                it[appointmentId] = dto.appointmentId
                it[name] = dto.name
                it[price] = dto.price
                it[loyaltyPoint] = dto.loyaltyPoint
            }[Services.id]
        }
    }

    override fun update(dto: ServiceDao): Int {
        logger.info("Execute query to update Service")
        return transaction {
            addLogger(StdOutSqlLogger)
            Services.update({ Services.id eq dto.id}) {
                it[appointmentId] = dto.appointmentId
                it[name] = dto.name
                it[price] = dto.price
                it[loyaltyPoint] = dto.loyaltyPoint
            }
        }
    }

    override fun upload(dto: List<ServiceDao>): Int {
        logger.info("Execute query to upload Service")
        return transaction {
            addLogger(StdOutSqlLogger)
            Services.batchInsert(dto) {
                this[Services.id] = it.id
                this[Services.appointmentId] = it.appointmentId
                this[Services.name] = it.name
                this[Services.price] = it.price
                this[Services.loyaltyPoint] = it.loyaltyPoint
            }.count()
        }
    }

    override fun getById(id: UUID): ServiceDao? {
        logger.info("Execute query to get Service")
        return transaction {
            addLogger(StdOutSqlLogger)
            Services.select { Services.id eq id }
                .map { getDto(it) }
                .firstOrNull()
        }
    }

    override fun delete(id: UUID): Int {
        logger.info("Execute query to delete Service")
        return transaction {
            addLogger(StdOutSqlLogger)
            Services.deleteWhere { Services.id eq id }
        }
    }

    private fun getDto(row: ResultRow): ServiceDao {
        return ServiceDao(
            row[Services.id],
            row[Services.appointmentId],
            row[Services.name],
            row[Services.price],
            row[Services.loyaltyPoint]
        )
    }

}