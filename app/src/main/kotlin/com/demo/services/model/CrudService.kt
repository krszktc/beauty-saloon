package com.demo.services.model

interface CrudService<O,I> {
    fun getAll(): List<O>
    fun getById(id: String): O?
    fun create(dto: I): String
    fun update(id: String, dto: I): Int
    fun upload(csv: ByteArray): Int
    fun delete(id: String): Int
}

