package com.demo.dto.serializers

import com.demo.dao.PurchaseDao
import com.demo.dto.PurchaseDto
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.util.*

class PurchaseSerializerTest: BehaviorSpec({

    given("Purchase") {
        val stringDao = "{" +
            "\"id\":\"a1234567-11aa-22bb-33cc-123456abcdef\"," +
            "\"appointmentId\":\"b1234567-11aa-22bb-33cc-123456abcdef\"," +
            "\"name\":\"PurchaseName\"," +
            "\"price\":20.0," +
            "\"loyaltyPoint\":10" +
        "}"

        val purchaseDao = PurchaseDao(
            UUID.fromString("a1234567-11aa-22bb-33cc-123456abcdef"),
            UUID.fromString("b1234567-11aa-22bb-33cc-123456abcdef"),
            "PurchaseName",
            20.0F,
            10
        )

        val purchaseDto = PurchaseDto(
            "b1234567-11aa-22bb-33cc-123456abcdef",
            "PurchaseName",
            20.0F,
            10
        )

        `when`("serialize Purchase Dto to json") {
            val json: String = PurchaseSerializer.serialize(purchaseDao)
            then("It should return sting representation") {
                json.shouldBe(stringDao)
            }
        }
        `when`("deserialize Purchase to Dao") {
            val dto: PurchaseDto = PurchaseSerializer.deserialize(stringDao)
            then("It should return Dto representation") {
                dto.shouldBe(purchaseDto)
            }
        }
    }

})