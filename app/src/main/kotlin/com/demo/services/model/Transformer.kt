package com.demo.services.model

import java.util.*

interface Transformer<O,I> {
    fun getFromCsv(csv: ByteArray): List<O>
    fun getFromDto(dto: I, id: UUID = UUID.randomUUID()): O
}