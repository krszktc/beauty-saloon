package com.demo.services.transformers

import com.demo.dao.ClientDao
import com.demo.dto.ClientDto
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.util.*

class ClientTransformerTest: BehaviorSpec({

    val client1 =
        "a1234567-11aa-22bb-33cc-123456abcdef," +
        "FirstName," +
        "FirstSurname," +
        "first.email@test.com," +
        "111-111-111," +
        "Male," +
        "false"

    val client2 =
        "b1234567-11aa-22bb-33cc-123456abcdef," +
        "SecondName," +
        "SecondSurname," +
        "second.email@test.com," +
        "222-222-222," +
        "Male," +
        "false"

    val client3 =
        "c1234567-11aa-22bb-33cc-123456abcdef," +
        "ThirdName," +
        "ThirdSurname," +
        "third.email@test.com," +
        "333-333-333," +
        "Male," +
        "false"

    given("Clients byte array") {
        val byteArray = "$client1\n$client2\n$client3".toByteArray()
        `when`("Transform CSV to Dao") {
            val listDto: List<ClientDao> = ClientTransformer.getFromCsv(byteArray)
            then("It should return list of Dao") {
                listDto.shouldBeInstanceOf<List<ClientDao>>()
                listDto.size.shouldBe(3)
                listDto[0].id.shouldBe(UUID.fromString("a1234567-11aa-22bb-33cc-123456abcdef"))
                listDto[1].email.shouldBe("second.email@test.com")
                listDto[2].phone.shouldBe("333-333-333")
            }
        }
    }

    given("Client Dto") {
        val dto = ClientDto(
            "TestName",
            "TestSurname",
            "test.email@test.com",
            "123-456-789",
            "Male"
        )
        `when`("Transform Dto to Dao") {
            val Dao = ClientTransformer.getFromDto(dto, UUID.randomUUID())
            then("It should return Dao") {
                Dao.shouldBeInstanceOf<ClientDao>()
                Dao.firstName.shouldBe(dto.firstName)
                Dao.lastName.shouldBe(dto.lastName)
                Dao.email.shouldBe(dto.email)
                Dao.phone.shouldBe(dto.phone)
                Dao.gender.shouldBe(dto.gender)
                Dao.banned.shouldBe(false)
            }
        }
    }

})