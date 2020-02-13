package com.demo.dao

import org.jetbrains.exposed.sql.Table
import java.util.*

object Purchases: Table("PURCHASES") {
    val id = uuid("id")
    val appointmentId = (uuid("appointment_id") references Appointments.id).index()
    val name = varchar("name", 50)
    val price = float("price")
    val loyaltyPoint = integer("loyalty_points")

    override val primaryKey = PrimaryKey(id, name = "PK_PURCHASE_ID")
}

data class PurchaseDao (
    val id: UUID,
    val appointmentId: UUID,
    val name: String,
    val price: Float,
    val loyaltyPoint: Int
)
