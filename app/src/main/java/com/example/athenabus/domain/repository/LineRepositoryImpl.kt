package com.example.athenabus.domain.repository

import com.example.athenabus.data.remote.OASATelematicsAPI
import com.example.athenabus.data.remote.dto.LineDto
import javax.inject.Inject

class LineRepositoryImpl @Inject constructor(
    private val api: OASATelematicsAPI
): LineRepository {

    override suspend fun getLines(): LineDto {
        return api.getLines()
    }
}