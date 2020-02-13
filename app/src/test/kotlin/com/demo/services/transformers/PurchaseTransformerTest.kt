package com.demo.services.transformers

import com.demo.dao.PurchaseDao
import com.demo.dto.PurchaseDto
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.util.*


class PurchaseTransformerTest: BehaviorSpec({

    val purchase1 =
        "a1234567-11aa-22bb-33cc-123456abcdef," +
                "b1234567-11aa-22bb-33cc-123456abcdef," +
                "ServiceOne," +
                "10.0," +
                "10"

    val purchase2 =
        "c1234567-11aa-22bb-33cc-123456abcdef," +
                "d1234567-11aa-22bb-33cc-123456abcdef," +
                "ServiceTwo," +
                "20.0," +
                "20"

    val purchase3 =
        "e1234567-11aa-22bb-33cc-123456abcdef," +
                "f1234567-11aa-22bb-33cc-123456abcdef," +
                "ServiceThree," +
                "30.0," +
                "30"

    given("Purchase byte array") {
        val byteArray = "$purchase1\n$purchase2\n$purchase3".toByteArray()
        `when`("Transform CSV to Dao") {
            val listDto: List<PurchaseDao> = PurchaseTransformer.getFromCsv(byteArray)
            then("It should return list of Dao") {
                listDto.shouldBeInstanceOf<List<PurchaseDao>>()
                listDto.size.shouldBe(3)
                listDto[0].id.shouldBe(UUID.fromString("a1234567-11aa-22bb-33cc-123456abcdef"))
                listDto[1].name.shouldBe("ServiceTwo")
                listDto[2].loyaltyPoint.shouldBe(30)
            }
        }
    }

    given("Purchase Dto") {
        val dto = PurchaseDto(
            "a1234567-11aa-22bb-33cc-123456abcdef",
            "PurchaseName",
            10.0F,
            10
        )
        `when`("Transform Dto to Dao") {
            val Dao = PurchaseTransformer.getFromDto(dto, UUID.randomUUID())
            then("It should return Dao") {
                Dao.shouldBeInstanceOf<PurchaseDao>()
                Dao.appointmentId.shouldBe(UUID.fromString(dto.appointmentId))
                Dao.name.shouldBe(dto.name)
                Dao.price.shouldBe(dto.price)
                Dao.loyaltyPoint.shouldBe(dto.loyaltyPoint)
            }
        }
    }

})