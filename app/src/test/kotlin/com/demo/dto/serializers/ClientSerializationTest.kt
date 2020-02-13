package com.demo.dto.serializers

import com.demo.dao.ClientDao
import com.demo.dto.ClientDto
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.util.*

class ClientSerializationTest: BehaviorSpec({

    given("Client") {
        val stringDao = "{" +
            "\"id\":\"a1234567-11aa-22bb-33cc-123456abcdef\"," +
            "\"firstName\":\"TestName\"," +
            "\"lastName\":\"TestSurname\"," +
            "\"email\":\"test.email@test.emai\"," +
            "\"phone\":\"111-222-333\"," +
            "\"gender\":\"Male\"," +
            "\"banned\":false" +
        "}"

        val clientDao = ClientDao(
            UUID.fromString("a1234567-11aa-22bb-33cc-123456abcdef"),
            "TestName",
            "TestSurname",
            "test.email@test.emai",
            "111-222-333",
            "Male",
            false
        )

        val clientDto = ClientDto(
            "TestName",
            "TestSurname",
            "test.email@test.emai",
            "111-222-333",
            "Male"
        )

        `when`("serialize Client Dto to json") {
            val json: String = ClientSerializer.serialize(clientDao)
            then("It should return sting representation") {
                json.shouldBe(stringDao)
            }
        }
        `when`("deserialize Client to Dao") {
            val dto: ClientDto = ClientSerializer.deserialize(stringDao)
            then("It should return Dto representation") {
                dto.shouldBe(clientDto)
            }
        }
    }

})