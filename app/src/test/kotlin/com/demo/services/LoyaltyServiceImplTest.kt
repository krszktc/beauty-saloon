package com.demo.services

import com.demo.dsl.LoyaltyReportDsl
import com.demo.dto.LoyaltyDto
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

class LoyaltyServiceImplTest: BehaviorSpec({

    val loyaltyDsl = mockk<LoyaltyReportDsl>()
    val service = LoyaltyServiceImpl(loyaltyDsl)

    given("Non empty Loyalty report") {
        every { loyaltyDsl.getLoyalty(any(), any()) } returns listOf(
            LoyaltyDto("11-22", 30),
            LoyaltyDto("33-44", 20),
            LoyaltyDto("55-66", 10)
        )
        `when`("Call get report") {
            val report = service.getLoyalty(20,"2019-01-01")
            then("") {
                report.size.shouldBe(3)
                report[0].id.shouldBe("11-22")
                report[1].id.shouldBe("33-44")
                report[2].id.shouldBe("55-66")
                report[0].loyaltyPoints.shouldBe(30)
                report[1].loyaltyPoints.shouldBe(20)
                report[2].loyaltyPoints.shouldBe(10)
            }
        }
    }

    given("Empty Loyalty report") {
        every { loyaltyDsl.getLoyalty(any(), any()) } returns emptyList()
        `when`("Call get report") {
            val report = service.getLoyalty(20,"2019-01-01")
            then("It should response with empty list") {
                report.shouldBeEmpty()
            }
        }
    }

})