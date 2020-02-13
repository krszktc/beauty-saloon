package com.demo.controllers.types

import com.demo.controllers.utils.getOkResponse
import com.demo.dto.model.Serializer
import com.demo.services.model.CrudService
import com.demo.services.utils.logger
import spark.Spark.*

class CrudController<O,I>(
    private val patch: String,
    private val service: CrudService<O,I>,
    private val serializer: Serializer<O,I>
) {
    private val logger = logger<CrudController<O,I>>()

    init {
        registerCrudRouting()
    }

    private fun registerCrudRouting() {
        logger.info("Registering CRUD for $patch ...")

        path("/api/$patch") {

            get("") { _, res ->
                logger.info("GetAll $patch")
                res.status(200)

                service.getAll()
                    .map { serializer.serialize(it) }
            }

            get("/:id") { req, res ->
                val id = req.params("id")
                logger.info("Get $patch by id: $id")

                val dto = service.getById(id)
                res.status(200)

                serializer.serialize(dto)

            }

            post("/create") { req, res ->
                val body = req.body()
                logger.info("Create $patch")
                logger.info(body)

                val dto = serializer.deserialize(body)
                val status = service.create(dto)
                res.status(200)

                getOkResponse(status)
            }

            put("/update/:id") { req, res ->
                val id = req.params("id")
                val body = req.body()
                logger.info("Update $patch by id: $id")
                logger.info(body)

                val dto = serializer.deserialize(body)
                val status = service.update(id, dto)
                res.status(200)

                getOkResponse("$status record updated")
            }

            post("/upload") { req, res ->
                logger.info("Uploading $patch ...")

                val status = service.upload(req.bodyAsBytes())
                res.status(200)

                getOkResponse("$status entities uploaded")
            }

            delete("/delete/:id") { req, res ->
                val id = req.params("id")
                logger.info("Deleting $patch by id: $id")

                val status = service.delete(id)
                res.status(200)

                getOkResponse("$status entity deleted")
            }

        }

    }

}