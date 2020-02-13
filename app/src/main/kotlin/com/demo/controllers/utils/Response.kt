package com.demo.controllers.utils

import com.demo.dto.StatusDto
import com.demo.dto.model.Status
import com.google.gson.Gson

fun getOkResponse(message: String): String {
    val response = StatusDto(Status.OK, message)
    return Gson().toJson(response)
}

fun getErrorResponse(message: String): String {
    val response = StatusDto(Status.ERROR, message)
    return Gson().toJson(response)
}