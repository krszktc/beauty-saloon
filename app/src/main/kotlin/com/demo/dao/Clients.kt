package com.demo.dao

import org.jetbrains.exposed.sql.Table
import java.util.*

object Clients: Table("CLIENTS") {
    val id = uuid("id")
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 100)
    val email = varchar("email", 100)
    val phone = varchar("phone", 20)
    val gender  = varchar("gender", 10)
    val banned = bool("banned")

    override val primaryKey = PrimaryKey(id, name = "PK_CLIENT_ID")
}

data class ClientDao (
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val gender: String,
    val banned: Boolean
)