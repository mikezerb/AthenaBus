package com.example.athenabus.domain.repository

import com.example.athenabus.data.remote.dto.LineDto

interface LineRepository {
    suspend fun getLines(): LineDto

}