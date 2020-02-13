package com.demo.dto.serializers

import com.demo.dao.AppointmentDao
import com.demo.dto.AppointmentDto
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import org.joda.time.DateTime
import java.util.*

class ApplicationSerializerTest: BehaviorSpec({

    given("Appointment") {
        val stringDao = "{" +
            "\"id\":\"a1234567-11aa-22bb-33cc-123456abcdef\"," +
            "\"clientId\":\"b1234567-11aa-22bb-33cc-123456abcdef\"," +
            "\"startTime\":\"2019-01-01 00:00:00\"," +
            "\"endTime\":\"2019-01-02 00:00:00\"" +
        "}"

        val appointmentDao = AppointmentDao(
            UUID.fromString("a1234567-11aa-22bb-33cc-123456abcdef"),
            UUID.fromString("b1234567-11aa-22bb-33cc-123456abcdef"),
            DateTime.parse("2019-01-01"),
            DateTime.parse("2019-01-02")
        )

        val appointmentDto = AppointmentDto(
            "b1234567-11aa-22bb-33cc-123456abcdef",
            DateTime.parse("2019-01-01"),
            DateTime.parse("2019-01-02")
        )

        `when`("serialize Appointment Dto to json") {
            val json: String = AppointmentSerializer.serialize(appointmentDao)
            then("It should return sting representation") {
                json.shouldBe(stringDao)
            }
        }
        `when`("deserialize Appointment to Dao") {
            val dto: AppointmentDto = AppointmentSerializer.deserialize(stringDao)
            then("It should return Dto representation") {
                dto.shouldBe(appointmentDto)
            }
        }
    }

})