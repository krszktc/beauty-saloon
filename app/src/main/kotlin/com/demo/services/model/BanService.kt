package com.demo.services.model

import com.demo.dto.BanDto

interface BanService {
    fun setBanned(dto: BanDto)
}