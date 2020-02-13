package com.demo.controllers.request

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

suspend fun getAsyncResponse(
    type: HttpType,
    endpoint: String,
    body: String = "{}"
): Pair<String?, Int> = withContext(Dispatchers.IO) {
    val json = "application/json; charset=utf-8".toMediaType()
    val requestBody = body.toRequestBody(json)
    val request = Request.Builder()
        .url("http://localhost:4567/api/$endpoint")

    when (type) {
        HttpType.GET -> request.get()
        HttpType.PUT -> request.put(requestBody)
        HttpType.POST -> request.post(requestBody)
        HttpType.PATCH -> request.patch(requestBody)
        HttpType.DELETE ->  request.delete(requestBody)
    }

    val response = OkHttpClient()
        .newCall(request.build())
        .execute()

    Pair(response.body?.string(), response.code)
}