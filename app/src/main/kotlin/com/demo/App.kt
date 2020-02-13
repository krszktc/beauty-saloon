package com.demo

import com.demo.dsl.utils.createDbSchema
import com.demo.controllers.ControllerBuilder
import com.demo.dsl.utils.fillByExampleData
import org.h2.tools.Server
import org.jetbrains.exposed.sql.*

fun main() {
    ControllerBuilder.registerRouting()

    Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start()
    Database.connect("jdbc:h2:mem:demo-db;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

    createDbSchema()
    fillByExampleData()
}