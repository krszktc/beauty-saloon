package com.demo.controllers.types

import com.demo.controllers.request.HttpType
import com.demo.controllers.request.getAsyncResponse
import com.demo.dto.LoyaltyDto
import com.demo.services.model.LoyaltyService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.util.*


class LoyaltyControllerTest: BehaviorSpec({

    val endpoint = "loyalty"
    val service = mockk<LoyaltyService<LoyaltyDto>>()

    LoyaltyController(endpoint, service)

    given("Loyalty DTO List") {
        every { service.getLoyalty(any(), any()) } returns listOf(
            LoyaltyDto("11-22-33", 40),
            LoyaltyDto("44-55-66", 30),
            LoyaltyDto("77-88-99", 20)
        )
        `when`("GET for report") {
            val response = getAsyncResponse(
                HttpType.GET,
                "$endpoint/10/2019-01-01"
            )
            val listType = object : TypeToken<ArrayList<LoyaltyDto>>() {}.type
            val dtoList: List<LoyaltyDto> = Gson().fromJson(response.first, listType)
            then("It should return report") {
                response.second.shouldBe(200)
                dtoList.size.shouldBe(3)
                dtoList.forEach { it.shouldBeInstanceOf<LoyaltyDto>() }
                dtoList[0].id.shouldBe("11-22-33")
                dtoList[1].id.shouldBe("44-55-66")
                dtoList[2].id.shouldBe("77-88-99")
                dtoList[0].loyaltyPoints.shouldBe(40)
                dtoList[1].loyaltyPoints.shouldBe(30)
                dtoList[2].loyaltyPoints.shouldBe(20)
            }
        }
    }

    given("Empty report for selected criteria") {
        every { service.getLoyalty(any(), any()) } returns emptyList()
        `when`("GET for report") {
            val response = getAsyncResponse(
                HttpType.GET,
                "$endpoint/10/2019-01-01"
            )
            then("It should return empty list") {
                response.second.shouldBe(200)
                response.first.shouldBe("[]")
            }
        }
    }

})
