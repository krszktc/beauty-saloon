package com.demo.services

import com.demo.dsl.model.BanDsl
import com.demo.dto.BanDto
import com.demo.services.model.StatusException
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

class BanServiceImplTest: BehaviorSpec({

    val banDsl = mockk<BanDsl>()
    val dto = BanDto("a1234567-11aa-22bb-33cc-123456abcdef",true)
    val service = BanServiceImpl(banDsl)

    given("Success banned Client") {
        every { banDsl.setBanned(any(), any()) } returns 1
        `when`("Call to ban by Id") {
            val result = service.setBanned(dto)
            then("It should return number or banned Clients") {
                result.shouldBe(Unit)
            }
        }
    }

    given("Non existing Client ") {
        every { banDsl.setBanned(any(), any()) } returns 0
        `when`("Call to ban by Id") {
            val exception = shouldThrow<StatusException> {
                service.setBanned(dto)
            }
            then("It should throw an error") {
                exception.message.shouldBe("Id doesn't exist")
            }
        }
    }

})