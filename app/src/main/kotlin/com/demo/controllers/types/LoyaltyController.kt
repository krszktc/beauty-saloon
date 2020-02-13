package com.demo.controllers.types

import com.demo.services.model.LoyaltyService
import com.demo.services.utils.logger
import com.google.gson.Gson
import spark.Spark.get

class LoyaltyController<T>(
    private val patch: String,
    private val service: LoyaltyService<T>
) {
    private val logger = logger<LoyaltyController<T>>()

    init {
        registerRouting()
    }

    private fun registerRouting() {
        logger.info("Registering Loyalty for $patch ...")
        get("/api/$patch/:count/:date") { req, res ->
            val count = req.params("count").toInt()
            val date = req.params("date")
            logger.info("Getting top $count Loyal clients from $date")

            res.status(200)
            service.getLoyalty(count, date)
                .map { serialize(it) }
        }
    }

    private fun serialize(dto: T): String {
        return Gson().toJson(dto)
    }
}