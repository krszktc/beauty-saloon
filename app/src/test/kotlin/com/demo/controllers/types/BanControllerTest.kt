package com.demo.controllers.types

import com.demo.controllers.request.HttpType
import com.demo.controllers.request.getAsyncResponse
import com.demo.dto.StatusDto
import com.demo.dto.model.Status
import com.demo.services.model.BanService
import com.demo.services.model.StatusException
import com.google.gson.Gson
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.mockk.*

class BanControllerTest: BehaviorSpec({

    val endpoint = "ban"
    val service = mockk<BanService>()

    BanController(endpoint, service)

    given("Properly updated ban status") {
        every { service.setBanned(any()) } returns Unit
        `when`("PATCH to ban Client"){
            val body = "{id: \"11-22-33\", status: true}"
            val response = getAsyncResponse(
                HttpType.PATCH,
                endpoint,
                body
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return ban status") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("Client 11-22-33 banned")
                response.second.shouldBe(200)
            }
        }
        `when`("Patch to revert ban for Client") {
            val body = "{id: \"44-55-66\", status: false}"
            val response = getAsyncResponse(
                HttpType.PATCH,
                endpoint,
                body
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return un-ban status") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("Client 44-55-66 un-banned")
                response.second.shouldBe(200)
            }
        }
    }

    given("Error by non existing ID") {
        every { service.setBanned(any()) } throws StatusException("Id doesn't exist")
        `when`("Request to ban non existing ID") {
            val body = "{id: \"11-22-33\", status: true}"
            val response = getAsyncResponse(
                HttpType.PATCH,
                endpoint,
                body
            )
            then("It should throw an error which will be catch by global handler") {
                response.first.shouldContain("Internal Server Error")
                response.second.shouldBe(500)
            }
        }
    }

})