package com.demo.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime
import java.util.*

object Appointments: Table("APPOINTMENTS") {
     val id = uuid("id")
     val clientId = (uuid("client_id") references Clients.id).index()
     val startTime = datetime("start_time")
     val endTime = datetime("end_time")

     override val primaryKey = PrimaryKey(id, name = "PK_APPOINTMENT_ID")
 }

data class AppointmentDao (
     val id: UUID,
     val clientId: UUID,
     val startTime: DateTime,
     val endTime: DateTime
)