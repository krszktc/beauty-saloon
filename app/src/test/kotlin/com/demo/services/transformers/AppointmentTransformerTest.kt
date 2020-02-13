package com.demo.services.transformers

import com.demo.dao.AppointmentDao
import com.demo.dto.AppointmentDto
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.joda.time.DateTime
import java.util.*

class AppointmentTransformerTest: BehaviorSpec({

    val appointment1 =
        "a1234567-11aa-22bb-33cc-123456abcdef," +
        "b1234567-11aa-22bb-33cc-123456abcdef," +
        "2019-01-01 00:00:00," +
        "2019-01-02 00:00:00"

    val appointment2 =
        "c1234567-11aa-22bb-33cc-123456abcdef," +
        "d1234567-11aa-22bb-33cc-123456abcdef," +
        "2019-01-03 00:00:00," +
        "2019-01-04 00:00:00"

    val appointment3 =
        "e1234567-11aa-22bb-33cc-123456abcdef," +
        "f1234567-11aa-22bb-33cc-123456abcdef," +
        "2019-01-05 00:00:00," +
        "2019-01-06 00:00:00"

    given("Appointment byte array") {
        val byteArray = "$appointment1\n$appointment2\n$appointment3".toByteArray()
        `when`("Transform CSV to Dao") {
            val listDto: List<AppointmentDao> = AppointmentTransformer.getFromCsv(byteArray)
            then("It should return list of Dao") {
                listDto.shouldBeInstanceOf<List<AppointmentDao>>()
                listDto.size.shouldBe(3)
                listDto[0].id.shouldBe(UUID.fromString("a1234567-11aa-22bb-33cc-123456abcdef"))
                listDto[1].clientId.shouldBe(UUID.fromString("d1234567-11aa-22bb-33cc-123456abcdef"))
            }
        }
    }

    given("Appointment Dto") {
        val dto = AppointmentDto(
            "a1234567-11aa-22bb-33cc-123456abcdef",
            DateTime.parse("2019-01-03"),
            DateTime.parse("2019-01-04")
        )
        `when`("Transform Dto to Dao") {
            val Dao = AppointmentTransformer.getFromDto(dto, UUID.randomUUID())
            then("It should return Dao") {
                Dao.shouldBeInstanceOf<AppointmentDao>()
                Dao.clientId.shouldBe(UUID.fromString(dto.clientId))
                Dao.startTime.shouldBe(dto.startTime)
                Dao.endTime.shouldBe(dto.endTime)
            }
        }
    }

})