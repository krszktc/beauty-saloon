package com.demo.controllers.types

import com.demo.controllers.request.HttpType
import com.demo.controllers.request.getAsyncResponse
import com.demo.dto.StatusDto
import com.demo.dto.model.Serializer
import com.demo.dto.model.Status
import com.demo.services.model.CrudService
import com.demo.services.model.StatusException
import com.google.gson.Gson
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk

class CrudControllerTest: BehaviorSpec({

    val endpoint = "crud"
    val mockDto = "{" +
        "\"id\":\"11-22-33\"," +
        "\"firstField\":\"firstValue\"," +
        "\"secondField\":\"secondValue\"" +
    "}"

    val service = mockk<CrudService<Any, Any>>()
    val serializer = mockk<Serializer<Any, Any>>()

    CrudController(endpoint, service, serializer)

    given("List of Dto for getAll") {
        every { service.getAll() } returns listOf(Any(), Any(), Any())
        every { serializer.serialize(any()) } returns mockDto
        `when`("Request to GET all dto") {
            val response =
                getAsyncResponse(HttpType.GET, endpoint)
            val listDto: List<String>? = response.first
                ?.substring(1, response.first!!.length -1)
                ?.split(", ")
            then("It should return all dto") {
                listDto.shouldNotBeNull()
                listDto.size.shouldBe(3)
                listDto.forEach { it.shouldBe(mockDto) }
                response.second.shouldBe(200)
            }
        }
    }

    given("Empty list of Dto for getAll") {
        every { service.getAll() } returns emptyList()
        every { serializer.serialize(any()) } returns mockDto
        `when`("request to GET all dto") {
            val response =
                getAsyncResponse(HttpType.GET, endpoint)
            then("It should return dto list") {
                response.first.shouldBe("[]")
                response.second.shouldBe(200)
            }
        }
    }

    given("Dto for getting by ID") {
        every { service.getById(any()) } returns Unit
        every { serializer.serialize(any()) } returns mockDto
        `when`("Request to GET dto details") {
            val response = getAsyncResponse(
                HttpType.GET,
                "$endpoint/11-22-33"
            )
            then("It should return dto") {
                response.first.shouldBe(mockDto)
                response.second.shouldBe(200)
            }
        }
    }

    given("Error by getting non existing id") {
        every { service.getById(any()) } throws StatusException("Id doesn't exist")
        `when`("Request to GET dto details") {
            val response = getAsyncResponse(
                HttpType.GET,
                "$endpoint/11-22-33"
            )
            then("It should throw an error which will be catch by global handler") {
                response.first.shouldContain("Internal Server Error")
                response.second.shouldBe(500)
            }
        }
    }

    given("Dto to create entity") {
        every { service.create(any()) } returns "111-222"
        every { serializer.deserialize(any()) } returns Unit
        `when`("Request to POST dto") {
            val response = getAsyncResponse(
                HttpType.POST,
                "$endpoint/create"
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return Id of new created entity") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("111-222")
                response.second.shouldBe(200)
            }
        }
    }

    given("One updated entity by existing id") {
        every { service.update(any(), any()) } returns 1
        every { serializer.deserialize(any()) } returns Unit
        `when`("Request to PUT dto") {
            val response = getAsyncResponse(
                HttpType.PUT,
                "$endpoint/update/11-22-33"
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return info about one updated entity") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("1 record updated")
                response.second.shouldBe(200)
            }
        }
    }

    given("None updated entity byt missing id") {
        every { service.update(any(), any()) } returns 0
        every { serializer.deserialize(any()) } returns Unit
        `when`("Request to PUT dto") {
            val response = getAsyncResponse(
                HttpType.PUT,
                "$endpoint/update/11-22-33"
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return info about no updated entity") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("0 record updated")
                response.second.shouldBe(200)
            }
        }
    }

    given("CSV file with multiple rows") {
        every { service.upload(any())} returns 99
        `when`("Request to POST csv") {
            val response = getAsyncResponse(
                HttpType.POST,
                "$endpoint/upload"
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return info about saved entities") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("99 entities uploaded")
                response.second.shouldBe(200)
            }
        }
    }

    given("Empty CSV file") {
        every { service.upload(any())} returns 0
        `when`("Request to POST csv") {
            val response = getAsyncResponse(
                HttpType.POST,
                "$endpoint/upload"
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return info about no entities to save") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("0 entities uploaded")
                response.second.shouldBe(200)
            }
        }
    }

    given("One deleted entity by existing id") {
        every { service.delete(any()) } returns 1
        `when`("Request to DELETE by id") {
            val response = getAsyncResponse(
                HttpType.DELETE,
                "$endpoint/delete/11-22-33"
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return info about one deleted entities") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("1 entity deleted")
                response.second.shouldBe(200)
            }
        }
    }

    given("None deleted entity by non existing id") {
        every { service.delete(any()) } returns 0
        `when`("Request to DELETE by id") {
            val response = getAsyncResponse(
                HttpType.DELETE,
                "$endpoint/delete/11-22-33"
            )
            val statusDto = Gson().fromJson(response.first, StatusDto::class.java)
            then("It should return info that none entity deleted") {
                statusDto.status.shouldBe(Status.OK)
                statusDto.message.shouldBe("0 entity deleted")
                response.second.shouldBe(200)
            }
        }
    }

})