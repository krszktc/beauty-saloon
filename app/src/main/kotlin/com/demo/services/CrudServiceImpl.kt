package com.demo.services

import com.demo.dsl.model.CrudDsl
import com.demo.services.model.CrudService
import com.demo.services.model.StatusException
import com.demo.services.model.Transformer
import com.demo.services.utils.getUUID
import com.demo.services.utils.logger
import java.util.*

class CrudServiceImpl<O,I>(
    private val validate: (I) -> String,
    private val dslService: CrudDsl<O>,
    private val transformer: Transformer<O,I>
): CrudService<O,I> {

    private val logger = logger<CrudServiceImpl<O,I>>()

    override fun getAll(): List<O>  {
        logger.info("Service to getAll results")
        return dslService.getAll()
    }

    override fun getById(id: String): O? {
        logger.info("Service to get by $id")
        return dslService.getById(getUUID(id))
            ?: throw StatusException("Id doesn't exist")
    }

    override fun create(dto: I): String {
        logger.info("Service to create entity")
        checkDto(dto)
        val dao = transformer.getFromDto(dto)
        return dslService.create(dao).toString()
    }

    override fun update(id: String, dto: I): Int {
        logger.info("Service to update $id")
        checkDto(dto)
        val uuid = UUID.fromString(id)
        val dao = transformer.getFromDto(dto, uuid)
        return dslService.update(dao)
    }

    override fun upload(csv: ByteArray): Int {
        logger.info("Service to upload entities")
        val daos = transformer.getFromCsv(csv)
        return dslService.upload(daos)
    }

    override fun delete(id: String): Int {
        logger.info("Service to delete $id")
        return dslService.delete(getUUID(id))
    }

    private fun checkDto(dto: I) {
        val validationStatus = validate(dto)
        if(validationStatus.isNotEmpty())
            throw StatusException(validationStatus)
    }

}