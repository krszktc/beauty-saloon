package com.demo.dto.serializers

import com.demo.dao.ServiceDao
import com.demo.dto.ServiceDto
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.util.*

class ServiceSerializerTest: BehaviorSpec({

    given("Service") {
        val stringDao = "{" +
            "\"id\":\"a1234567-11aa-22bb-33cc-123456abcdef\"," +
            "\"appointmentId\":\"b1234567-11aa-22bb-33cc-123456abcdef\"," +
            "\"name\":\"PurchaseName\"," +
            "\"price\":20.0," +
            "\"loyaltyPoint\":10" +
        "}"

        val serviceDao = ServiceDao(
            UUID.fromString("a1234567-11aa-22bb-33cc-123456abcdef"),
            UUID.fromString("b1234567-11aa-22bb-33cc-123456abcdef"),
            "PurchaseName",
            20.0F,
            10
        )

        val serviceDto = ServiceDto(
            "b1234567-11aa-22bb-33cc-123456abcdef",
            "PurchaseName",
            20.0F,
            10
        )

        `when`("serialize Service Dto to json") {
            val json: String = ServiceSerializer.serialize(serviceDao)
            then("It should return sting representation") {
                json.shouldBe(stringDao)
            }
        }
        `when`("deserialize Service to Dao") {
            val dto: ServiceDto = ServiceSerializer.deserialize(stringDao)
            then("It should return Dto representation") {
                dto.shouldBe(serviceDto)
            }
        }
    }

})