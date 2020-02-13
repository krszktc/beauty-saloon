package com.demo.controllers.types

import com.demo.controllers.utils.getOkResponse
import com.demo.dto.BanDto
import com.demo.services.model.BanService
import com.demo.services.utils.logger
import com.google.gson.Gson
import spark.Spark.patch

class BanController(
    private val patch: String,
    private val service: BanService
) {

    private val logger = logger<BanController>()

    init {
        registerRouting()
    }

    private fun registerRouting() {
        logger.info("Registering Ban for $patch ...")
        patch("/api/$patch") { req, res ->
            val dto = deserialize(req.body())
            val banned = when {
                dto.status -> "banned"
                else -> "un-banned"
            }

            logger.info("Switching Client ${dto.id} to be $banned")
            service.setBanned(dto)
            res.status(200)

            getOkResponse("Client ${dto.id} $banned")
        }
    }

    private fun deserialize(json: String): BanDto {
        return Gson().fromJson(json, BanDto::class.java)
    }
}