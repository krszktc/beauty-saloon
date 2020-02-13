package com.demo.dsl.model

import java.util.*

interface CrudDsl<T> {
    fun getAll(): List<T>
    fun getById(id: UUID): T?
    fun create(dto: T): UUID
    fun update(dto: T): Int
    fun upload(dto: List<T>): Int
    fun delete(id: UUID): Int
}

