package com.demo.dto.model

interface Serializer<O,I> {
    fun serialize(dao: O?): String
    fun deserialize(json: String): I
}