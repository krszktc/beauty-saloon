package com.demo.services.utils

import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.StringSpec

class CheckerTests: StringSpec({

    "UUIDs should pass" {
        isUUID("a1234567-11aa-22bb-33cc-123456abcdef").shouldBeTrue()
        isUUID("aaaa1111-11aa-11aa-11aa-111111aaaaaa").shouldBeTrue()
        isUUID("aaaaaaaa-1111-1111-1111-aaaaaaaaaaaa").shouldBeTrue()
    }

    "UUIDs shouldn't pass" {
        isUUID("aaaaaaaab-1111-1111-1111-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaa-1111-1111-1111-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-11112-1111-1111-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-111-1111-1111-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-1111-11112-1111-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-1111-111-1111-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-1111-1111-11112-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-1111-1111-111-aaaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-1111-1111-1111-aaaaaaaaaaaab").shouldBeFalse()
        isUUID("aaaaaaaa-1111-1111-1111-aaaaaaaaaaa").shouldBeFalse()
        isUUID("aaaaaaaa-1111-1111-1111").shouldBeFalse()
        isUUID("aaaaaaaa-1111-1111").shouldBeFalse()
    }

    "Emails should pass" {
        isEmail("aaa@bbb.com").shouldBeTrue()
        isEmail("aaa.bbb@ccc.com.eu").shouldBeTrue()
        isEmail("aaa.bbb.ccc@ddd.com.eu").shouldBeTrue()
        isEmail("aaa_bbb-ccc@ddd.com.eu").shouldBeTrue()
        isEmail("1aaa8bbb11ccc@ddd.eu").shouldBeTrue()
    }

    "Emails shouldn't pass" {
        isEmail("aaabbb.com").shouldBeFalse()
        isEmail("aaa@com").shouldBeFalse()
        isEmail("@com").shouldBeFalse()
        isEmail("aa$%@#@com").shouldBeFalse()
        isEmail("aa$%@#@com.eu").shouldBeFalse()
        isEmail("com").shouldBeFalse()
        isEmail("com@").shouldBeFalse()
        isEmail("aaa@bb@com.eu").shouldBeFalse()
    }

    "Phones should pass" {
        isPhoneNumber("111-222-333").shouldBeTrue()
        isPhoneNumber("111.222.333").shouldBeTrue()
        isPhoneNumber("111.222-333").shouldBeTrue()
        isPhoneNumber("1-11 22.33").shouldBeTrue()
        isPhoneNumber("11 222 333 44").shouldBeTrue()
        isPhoneNumber("12 11-22 33").shouldBeTrue()
        isPhoneNumber("12 11-22.33").shouldBeTrue()
        isPhoneNumber("(112) 1.22-333").shouldBeTrue()
        isPhoneNumber("(112)-1.22-333 444").shouldBeTrue()
        isPhoneNumber("(122)-11-22-33").shouldBeTrue()

    }

    "Phones shouldn't pass" {
        isPhoneNumber("11-22-3a").shouldBeFalse()
        isPhoneNumber("(11-22-33").shouldBeFalse()
        isPhoneNumber("()11-22-33").shouldBeFalse()
        isPhoneNumber("(1)11-22-33").shouldBeFalse()
        isPhoneNumber("(12)11-22-33").shouldBeFalse()
        isPhoneNumber("1234567890").shouldBeFalse()
        isPhoneNumber("").shouldBeFalse()
        isPhoneNumber("").shouldBeFalse()
        isPhoneNumber(".").shouldBeFalse()
        isPhoneNumber(".1.").shouldBeFalse()
    }

    "Length should pass" {
        hasProperLength("abc").shouldBeTrue()
        hasProperLength("abcd").shouldBeTrue()
    }

    "Length shouldn't pass" {
        hasProperLength("        ").shouldBeFalse()
        hasProperLength("").shouldBeFalse()
        hasProperLength("a").shouldBeFalse()
        hasProperLength("ab").shouldBeFalse()
    }

    "Price should pass" {
        isPriceDefined(1.0F).shouldBeTrue()
        isPriceDefined(99.9F).shouldBeTrue()
    }

    "Price shouldn't pass" {
        isPriceDefined(0.9F).shouldBeFalse()
        isPriceDefined(0.1F).shouldBeFalse()
        isPriceDefined(0.0F).shouldBeFalse()
        isPriceDefined(-0.1F).shouldBeFalse()
    }

    "Loyalty should pass" {
        isLoyaltyDefined(1).shouldBeTrue()
        isLoyaltyDefined(999).shouldBeTrue()
    }

    "Loyalty shouldn't pass" {
        isLoyaltyDefined(0).shouldBeFalse()
        isLoyaltyDefined(-1).shouldBeFalse()
        isLoyaltyDefined(-999).shouldBeFalse()
    }

    "Gender should pass" {
        isGender("Male").shouldBeTrue()
        isGender("Female").shouldBeTrue()
    }

    "Gender shouldn't pass" {
        isGender("").shouldBeFalse()
        isGender("male").shouldBeFalse()
        isGender("female").shouldBeFalse()
        isGender("mAle").shouldBeFalse()
        isGender("FemAle").shouldBeFalse()
        isGender("bala bla bla").shouldBeFalse()
    }

    "Date order should pass" {
        isProperDateOrder(
            getDateTime("2019-01-01 12:00:00"),
            getDateTime("2019-01-01 12:00:01")
        ).shouldBeTrue()

        isProperDateOrder(
            getDateTime("2019-01-01 12:00:00"),
            getDateTime("2019-01-02 12:00:00")
        ).shouldBeTrue()

        isProperDateOrder(
            getDateTime("2019-01-01 12:00:00"),
            getDateTime("2019-01-01 12:10:00")
        ).shouldBeTrue()

        isProperDateOrder(
            getDateTime("2019-01-01 12:00:00"),
            getDateTime("2019-01-01 13:00:00")
        ).shouldBeTrue()
    }

    "Date order shouldn't pass" {
        isProperDateOrder(
            getDateTime("2019-01-01 12:00:00"),
            getDateTime("2019-01-01 12:00:00")
        ).shouldBeFalse()

        isProperDateOrder(
            getDateTime("2019-01-01 12:00:01"),
            getDateTime("2019-01-01 12:00:00")
        ).shouldBeFalse()

        isProperDateOrder(
            getDateTime("2019-01-02 12:00:00"),
            getDateTime("2019-01-01 12:00:00")
        ).shouldBeFalse()

        isProperDateOrder(
            getDateTime("2019-01-01 13:00:00"),
            getDateTime("2019-01-01 12:00:00")
        ).shouldBeFalse()
    }

})