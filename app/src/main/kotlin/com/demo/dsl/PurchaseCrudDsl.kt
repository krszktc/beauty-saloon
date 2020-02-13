package com.demo.dsl

import com.demo.dao.PurchaseDao
import com.demo.dao.Purchases
import com.demo.dsl.model.CrudDsl
import com.demo.services.utils.logger
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object PurchaseCrudDsl: CrudDsl<PurchaseDao> {

    private val logger = logger<PurchaseCrudDsl>()

    override fun getAll(): List<PurchaseDao> {
        logger.info("Execute query to get all Purchases")
        return transaction {
            addLogger(StdOutSqlLogger)
            Purchases.selectAll().map { getDto(it) }
        }
    }

    override fun create(dto: PurchaseDao): UUID {
        logger.info("Execute query to create Purchase")
        return transaction {
            addLogger(StdOutSqlLogger)
            Purchases.insert {
                it[id] = dto.id
                it[appointmentId] = dto.appointmentId
                it[name] = dto.name
                it[price] = dto.price
                it[loyaltyPoint] = dto.loyaltyPoint
            }[Purchases.id]
        }
    }

    override fun update(dto: PurchaseDao): Int {
        logger.info("Execute query to update Purchase")
        return transaction {
            addLogger(StdOutSqlLogger)
            Purchases.update({ Purchases.id eq dto.id}) {
                it[appointmentId] = dto.appointmentId
                it[name] = dto.name
                it[price] = dto.price
                it[loyaltyPoint] = dto.loyaltyPoint
            }
        }
    }

    override fun upload(dto: List<PurchaseDao>): Int {
        logger.info("Execute query to upload Purchases")
        return transaction {
            addLogger(StdOutSqlLogger)
            Purchases.batchInsert(dto) {
                this[Purchases.id] = it.id
                this[Purchases.appointmentId] = it.appointmentId
                this[Purchases.name] = it.name
                this[Purchases.price] = it.price
                this[Purchases.loyaltyPoint] = it.loyaltyPoint
            }.count()
        }
    }

    override fun getById(id: UUID): PurchaseDao? {
        logger.info("Execute query to get Purchase")
        return transaction {
            addLogger(StdOutSqlLogger)
            Purchases.select { Purchases.id eq id }
                .map { getDto(it) }
                .firstOrNull()
        }
    }

    override fun delete(id: UUID): Int {
        logger.info("Execute query to delete Purchase")
        return transaction {
            addLogger(StdOutSqlLogger)
            Purchases.deleteWhere { Purchases.id eq id }
        }
    }

    private fun getDto(row: ResultRow): PurchaseDao {
        return PurchaseDao(
            row[Purchases.id],
            row[Purchases.appointmentId],
            row[Purchases.name],
            row[Purchases.price].toFloat(),
            row[Purchases.loyaltyPoint].toInt()
        )
    }
}