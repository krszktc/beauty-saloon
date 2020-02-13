package com.demo.dsl

import com.demo.dao.ClientDao
import com.demo.dao.Clients
import com.demo.dsl.model.BanDsl
import com.demo.dsl.model.CrudDsl
import com.demo.services.utils.logger
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object ClientCrudDsl: BanDsl, CrudDsl<ClientDao> {

    private val logger = logger<ClientCrudDsl>()

    override fun getAll(): List<ClientDao> {
        logger.info("Execute query to get all Clients")
        return transaction {
            addLogger(StdOutSqlLogger)
            Clients.selectAll()
                .map { getDto(it) }
        }
    }

    override fun create(dto: ClientDao): UUID {
        logger.info("Execute query to create Client")
        return transaction {
            addLogger(StdOutSqlLogger)
            Clients.insert {
                it[id] = dto.id
                it[firstName] = dto.firstName
                it[lastName] = dto.lastName
                it[email] = dto.email
                it[phone] = dto.phone
                it[gender] = dto.gender
                it[banned] = dto.banned
            }[Clients.id]
        }
    }

    override fun update(dto: ClientDao): Int {
        logger.info("Execute query to update Client")
        return transaction {
            addLogger(StdOutSqlLogger)
            Clients.update({ Clients.id eq dto.id}) {
                it[firstName] = dto.firstName
                it[lastName] = dto.lastName
                it[email] = dto.email
                it[phone] = dto.phone
                it[gender] = dto.gender
            }
        }
    }

    override fun upload(dto: List<ClientDao>): Int {
        logger.info("Execute query to upload Clients")
        return transaction {
            addLogger(StdOutSqlLogger)
            Clients.batchInsert(dto) {
                this[Clients.id] = it.id
                this[Clients.firstName] = it.firstName
                this[Clients.lastName] = it.lastName
                this[Clients.email] = it.email
                this[Clients.phone] = it.phone
                this[Clients.gender] = it.gender
                this[Clients.banned] = it.banned
            }.count()
        }
    }

    override fun getById(id: UUID): ClientDao? {
        logger.info("Execute query to get Client")
        return transaction {
            addLogger(StdOutSqlLogger)
            Clients.select { Clients.id eq id }
                .map { getDto(it) }
                .firstOrNull()
        }
    }

    override fun delete(id: UUID): Int {
        logger.info("Execute query to delete Client")
        return transaction {
            addLogger(StdOutSqlLogger)
            Clients.deleteWhere { Clients.id eq id }
        }
    }

    override fun setBanned(id: UUID, baned: Boolean): Int {
        logger.info("Execute query to active/ban Client")
        return transaction {
            addLogger(StdOutSqlLogger)
            Clients.update({ Clients.id eq id}) {
                it[banned] = baned
            }
        }
    }

    private fun getDto(row: ResultRow): ClientDao {
        return ClientDao(
            row[Clients.id],
            row[Clients.firstName],
            row[Clients.lastName],
            row[Clients.email],
            row[Clients.phone],
            row[Clients.gender],
            row[Clients.banned]
        )
    }

}