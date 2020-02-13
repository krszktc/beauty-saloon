package com.demo.controllers.utlis

import com.demo.controllers.utils.getErrorResponse
import com.demo.controllers.utils.getOkResponse
import com.demo.dto.StatusDto
import com.demo.dto.model.Status
import com.google.gson.Gson
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class ResponseTest: BehaviorSpec({

    given("message for OK status"){
        val message = "Test OK Message"
        `when`("call function to get OK status") {
            val dto = Gson().fromJson(getOkResponse(message), StatusDto::class.java)
            then("It should return OK DTO with passed message") {
                dto.shouldBeInstanceOf<StatusDto>()
                dto.message.shouldBe(message)
                dto.status.shouldBe(Status.OK)
            }
        }
    }

    given("message for ERROR status"){
        val message = "Test Error Message"
        `when`("call function to get ERROR status") {
            val dto = Gson().fromJson(getErrorResponse(message), StatusDto::class.java)
            then("It should return ERROR DTO with passed message") {
                dto.shouldBeInstanceOf<StatusDto>()
                dto.message.shouldBe(message)
                dto.status.shouldBe(Status.ERROR)
            }
        }
    }

})