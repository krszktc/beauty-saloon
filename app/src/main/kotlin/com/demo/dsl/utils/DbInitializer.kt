package com.demo.dsl.utils

import com.demo.dao.Appointments
import com.demo.dao.Clients
import com.demo.dao.Purchases
import com.demo.dao.Services
import com.demo.services.utils.getDateTime
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun createDbSchema() {
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create (Clients, Services, Appointments, Purchases)
    }
}

fun fillByExampleData() {
    transaction {
        addLogger(StdOutSqlLogger)

        Clients.insert {
            it[id] = UUID.fromString("a0000001-11aa-11aa-11aa-111111aaaaaa")
            it[firstName] = "One"
            it[lastName] = "One"
            it[email] = "one@one.com"
            it[phone] = "111-111-111"
            it[gender] = "Male"
            it[banned] = false
        }

        Clients.insert {
            it[id] = UUID.fromString("a0000002-11aa-11aa-11aa-111111aaaaaa")
            it[firstName] = "Two"
            it[lastName] = "Two"
            it[email] = "two@com.com"
            it[phone] = "222-222-222"
            it[gender] = "Male"
            it[banned] = false
        }

        Clients.insert {
            it[id] = UUID.fromString("a0000003-11aa-11aa-11aa-111111aaaaaa")
            it[firstName] = "Three"
            it[lastName] = "Three"
            it[email] = "three@com.com"
            it[phone] = "333-333-333"
            it[gender] = "Female"
            it[banned] = false
        }

        Clients.insert {
            it[id] = UUID.fromString("a0000004-11aa-11aa-11aa-111111aaaaaa")
            it[firstName] = "Four"
            it[lastName] = "Four"
            it[email] = "four@com.com"
            it[phone] = "444-444-444"
            it[gender] = "Female"
            it[banned] = false
        }


        Clients.insert {
            it[id] = UUID.fromString("a0000005-11aa-11aa-11aa-111111aaaaaa")
            it[firstName] = "Banned"
            it[lastName] = "Banned"
            it[email] = "banned@com.com"
            it[phone] = "999-999-999"
            it[gender] = "Female"
            it[banned] = true
        }

        Appointments.insert {
            it[id] = UUID.fromString("b0000001-11aa-11aa-11aa-111111aaaaaa")
            it[clientId] = UUID.fromString("a0000001-11aa-11aa-11aa-111111aaaaaa")
            it[startTime] = getDateTime("2019-01-01 10:00:00")
            it[endTime] = getDateTime("2019-01-01 10:30:00")
        }

        Appointments.insert {
            it[id] = UUID.fromString("b0000002-11aa-11aa-11aa-111111aaaaaa")
            it[clientId] = UUID.fromString("a0000002-11aa-11aa-11aa-111111aaaaaa")
            it[startTime] = getDateTime("2019-01-02 11:00:00")
            it[endTime] = getDateTime("2019-01-02 11:30:00")
        }

        Appointments.insert {
            it[id] = UUID.fromString("b0000003-11aa-11aa-11aa-111111aaaaaa")
            it[clientId] = UUID.fromString("a0000002-11aa-11aa-11aa-111111aaaaaa")
            it[startTime] = getDateTime("2019-01-03 12:00:00")
            it[endTime] = getDateTime("2019-01-03 12:40:00")
        }

        Appointments.insert {
            it[id] = UUID.fromString("b0000004-11aa-11aa-11aa-111111aaaaaa")
            it[clientId] = UUID.fromString("a0000003-11aa-11aa-11aa-111111aaaaaa")
            it[startTime] = getDateTime("2019-01-04 10:00:00")
            it[endTime] = getDateTime("2019-01-04 10:30:00")
        }

        Appointments.insert {
            it[id] = UUID.fromString("b0000005-11aa-11aa-11aa-111111aaaaaa")
            it[clientId] = UUID.fromString("a0000003-11aa-11aa-11aa-111111aaaaaa")
            it[startTime] = getDateTime("2019-01-05 11:00:00")
            it[endTime] = getDateTime("2019-01-05 10:30:00")
        }

        Appointments.insert {
            it[id] = UUID.fromString("b0000006-11aa-11aa-11aa-111111aaaaaa")
            it[clientId] = UUID.fromString("a0000003-11aa-11aa-11aa-111111aaaaaa")
            it[startTime] = getDateTime("2019-01-06 12:00:00")
            it[endTime] = getDateTime("2019-01-06 12:30:00")
        }

        Appointments.insert {
            it[id] = UUID.fromString("b0000007-11aa-11aa-11aa-111111aaaaaa")
            it[clientId] = UUID.fromString("a0000005-11aa-11aa-11aa-111111aaaaaa")
            it[startTime] = getDateTime("2019-01-06 12:00:00")
            it[endTime] = getDateTime("2019-01-06 12:30:00")
        }

        Services.insert {
            it[id] = UUID.fromString("c0000001-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000001-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Service 1"
            it[price] =  5.5F
            it[loyaltyPoint] = 5
        }

        Services.insert {
            it[id] = UUID.fromString("c0000002-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000002-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Service 2"
            it[price] =  10.0F
            it[loyaltyPoint] = 10
        }

        Services.insert {
            it[id] = UUID.fromString("c0000003-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000003-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Service 3"
            it[price] =  15.5F
            it[loyaltyPoint] = 15
        }

        Services.insert {
            it[id] = UUID.fromString("c0000004-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000004-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Service 4"
            it[price] =  20.0F
            it[loyaltyPoint] = 20
        }

        Services.insert {
            it[id] = UUID.fromString("c0000005-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000007-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Banned Service"
            it[price] =  10.0F
            it[loyaltyPoint] = 10
        }

        Purchases.insert {
            it[id] = UUID.fromString("d0000001-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000001-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Purchase 1"
            it[price] =  7.5F
            it[loyaltyPoint] = 5
        }

        Purchases.insert {
            it[id] = UUID.fromString("d0000002-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000002-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Purchase 2"
            it[price] =  10.0F
            it[loyaltyPoint] = 10
        }

        Purchases.insert {
            it[id] = UUID.fromString("d0000003-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000003-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Purchase 3"
            it[price] =  22.5F
            it[loyaltyPoint] = 20
        }

        Purchases.insert {
            it[id] = UUID.fromString("d0000004-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000005-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Purchase 4"
            it[price] =  30.0F
            it[loyaltyPoint] = 30
        }

        Purchases.insert {
            it[id] = UUID.fromString("d0000005-11aa-11aa-11aa-111111aaaaaa")
            it[appointmentId] = UUID.fromString("b0000007-11aa-11aa-11aa-111111aaaaaa")
            it[name] = "Banned Purchase"
            it[price] =  10.0F
            it[loyaltyPoint] = 10
        }

    }

}