package com.demo.dsl.model

import java.util.*

interface BanDsl {
    fun setBanned(id: UUID, baned: Boolean): Int
}