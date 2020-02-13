package com.demo.dsl

import com.demo.dao.AppointmentDao
import com.demo.dao.Appointments
import com.demo.dsl.model.CrudDsl
import com.demo.services.utils.logger
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object AppointmentCrudDsl: CrudDsl<AppointmentDao> {

    private val logger = logger<AppointmentCrudDsl>()

    override fun getAll(): List<AppointmentDao> {
        logger.info("Execute query to get all Appointments")
        return transaction {
            addLogger(StdOutSqlLogger)
            Appointments.selectAll()
                .map { getDto(it) }
        }
    }

    override fun create(dto: AppointmentDao): UUID {
        logger.info("Execute query to create Appointment")
        return transaction {
            addLogger(StdOutSqlLogger)
            Appointments.insert {
                it[id] = dto.id
                it[clientId] = dto.clientId
                it[startTime] = dto.startTime
                it[endTime] = dto.endTime
            }[Appointments.id]
        }
    }

    override fun update(dto: AppointmentDao): Int {
        logger.info("Execute query to update Appointment")
        return transaction {
            addLogger(StdOutSqlLogger)
            Appointments.update({ Appointments.id eq dto.id }) {
                it[clientId] = dto.clientId
                it[startTime] = dto.startTime
                it[endTime] = dto.endTime
            }
        }
    }

    override fun upload(dto: List<AppointmentDao>): Int {
        logger.info("Execute query to upload Appointments")
        return transaction {
            addLogger(StdOutSqlLogger)
            Appointments.batchInsert(dto) {
                this[Appointments.id] = it.id
                this[Appointments.clientId] = it.clientId
                this[Appointments.startTime] = it.startTime
                this[Appointments.endTime] = it.endTime
            }.count()
        }
    }

    override fun getById(id: UUID): AppointmentDao? {
        logger.info("Execute query to get Appointment")
        return transaction {
            addLogger(StdOutSqlLogger)
            Appointments.select { Appointments.id eq id }
                .map { getDto(it) }
                .firstOrNull()
        }
    }

    override fun delete(id: UUID): Int {
        logger.info("Execute query to delete Appointment")
        return transaction {
            addLogger(StdOutSqlLogger)
            Appointments.deleteWhere { Appointments.id eq id }
        }
    }

    private fun getDto(row: ResultRow): AppointmentDao {
        return AppointmentDao(
            row[Appointments.id],
            row[Appointments.clientId],
            row[Appointments.startTime],
            row[Appointments.endTime]
        )
    }

}