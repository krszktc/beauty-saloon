package com.demo.services

import com.demo.dsl.model.CrudDsl
import com.demo.services.model.StatusException
import com.demo.services.model.Transformer
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import java.util.*

class CrudServiceImplTest: BehaviorSpec({

    val validate = mockk<(Any) -> String>()
    val dslService = mockk<CrudDsl<Any>>()
    val transformer = mockk<Transformer<Any, Any>>()

    val service = CrudServiceImpl(validate, dslService, transformer)

    given("Non empty list of Dao") {
        every { dslService.getAll() } returns listOf(Any(), Any(), Any())
        `when`("Call to getAll") {
            val result = service.getAll()
            then("It should return Dao list") {
                result.size.shouldBe(3)
            }
        }
    }

    given("Dao for searching by Id") {
        every { dslService.getById(any()) } returns Any()
        `when`("Call to getById") {
            val result = service.getById("a1234567-11aa-22bb-33cc-123456abcdef")
            then("It should return Dao") {
                result.shouldNotBeNull()
            }
        }
        `when`("Call with non UUID param") {
            val exception = shouldThrow<IllegalArgumentException> {
                service.getById("11-22")
            }
            then("Should throw IllegalArgumentException")
            exception.message.shouldContain("Invalid UUID string")
        }
    }

    given("Non existing Dao"){
        every { dslService.getById(any()) } returns null
        `when`("Call to getById") {
            val exception = shouldThrow<StatusException> {
                service.getById("a1234567-11aa-22bb-33cc-123456abcdef")
            }
            then("It should throw StatusException") {
                exception.message.shouldBe("Id doesn't exist")
            }
        }
    }

    given("Id of created Dao") {
        val uuid = UUID.randomUUID()
        every { transformer.getFromDto(any(), any()) } returns Any()
        every { dslService.create(any()) } returns uuid
        `when`("Create with positive validation") {
            every { validate(any()) } returns ""
            val result = service.create(Any())
            then("It should return id of created entity") {
                result.shouldBe(uuid.toString())
            }
        }
        `when`("Create with failed validation") {
            every { validate(any()) } returns "Validation fail info"
            val exception = shouldThrow<StatusException> {
                service.create(Any())
            }
            then("It should throw StatusException") {
                exception.message.shouldBe("Validation fail info")
            }
        }
    }

    given("Id of updated Dao") {
        every { transformer.getFromDto(any(), any()) } returns Any()
        `when`("Update with failed validation") {
            every { validate(any()) } returns "Validation info"
            val exception = shouldThrow<StatusException> {
                service.update("111-222", Any())
            }
            then("It should throw StatusException") {
                exception.message.shouldBe("Validation info")
            }
        }
        `when`("Update existing id") {
            every { validate(any()) } returns ""
            every { dslService.update(any()) } returns 1
            val result = service.update("a1234567-11aa-22bb-33cc-123456abcdef", Any())
            then("It should return 1 (updated entity)") {
                result.shouldBe(1)
            }
        }
        `when`("Update non-existing id"){
            every { validate(any()) } returns ""
            every { dslService.update(any()) } returns 0
            val result = service.update("a1234567-11aa-22bb-33cc-123456abcdef", Any())
            then("It should return 0 (updated entity)") {
                result.shouldBe(0)
            }
        }
        `when`("Update non UUID") {
            every { validate(any()) } returns ""
            val exception = shouldThrow<IllegalArgumentException> {
                service.update("111-222", Any())
            }
            then("Should throw StatusException") {
                exception.message.shouldContain("Invalid UUID string")
            }
        }
    }

    given("List of uploaded Daos") {
        every { transformer.getFromCsv(any()) } returns listOf(Any(), Any(), Any())
        `when`("Upload from non empty Csv") {
            every { dslService.upload(any()) } returns 69
            val result = service.upload("ByteArray".toByteArray())
            then("It should return number of uploaded entities") {
                result.shouldBe(69)
            }
        }
        `when`("Upload empty Csv") {
            every { dslService.upload(any()) } returns 0
            val result = service.upload("".toByteArray())
            then("It should return 0 (uploaded entities)") {
                result.shouldBe(0)
            }
        }
    }

    given("Number of deleted Dao") {
        every { dslService.delete(any()) } returns 1
        `when`("Call to delete") {
            val result = service.delete("a1234567-11aa-22bb-33cc-123456abcdef")
            then("It should return number of deleted entities") {
                result.shouldBe(1)
            }
        }
        `when`("Call to delete non UUID") {
            val exception = shouldThrow<IllegalArgumentException> {
                service.delete("111-222")
            }
            then("Should throw StatusException") {
                exception.message.shouldContain("Invalid UUID string")
            }
        }
    }

    given("Non existing id to delete") {
        every { dslService.delete(any()) } returns 0
        `when`("Call to delete") {
            val result = service.delete("a1234567-11aa-22bb-33cc-123456abcdef")
            then("It should return 0 (deleted entities)") {
                result.shouldBe(0)
            }
        }
    }

})