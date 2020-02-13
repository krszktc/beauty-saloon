package com.demo.services.utils

import com.opencsv.CSVReader
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.*

const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

fun getUUID(id: String): UUID {
    return UUID.fromString(id)
}

fun getReader(byteArray: ByteArray): CSVReader {
    val inputStream = InputStreamReader(ByteArrayInputStream(byteArray))
    return CSVReader(inputStream)
}

fun parseForSingleUUID(byteArray: ByteArray): List<Array<String>> {
    return getReader(byteArray).readAll()
        .filter {
            it.size > 1
                && isUUID(it[0])
        }
}

fun parseForDoubleUUID(byteArray: ByteArray): List<Array<String>> {
    return getReader(byteArray).readAll()
        .filter {
            it.size > 2
                && isUUID(it[0])
                && isUUID(it[1])
        }
}

fun getDateTime(dateTime: String): DateTime {
    val formatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT)
    val fixedDate = dateTime.take(DATE_TIME_FORMAT.length)
    return formatter.parseDateTime(fixedDate)
}

fun parseDateTime(dateTime: DateTime?): String {
    val formatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT)
    return formatter.print(dateTime)
}



