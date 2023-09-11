package com.example.athenabus.domain.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import kotlinx.coroutines.flow.Flow

interface BusLineRepository {
    fun getBusLines() : Flow<Resource<List<Line>>>
}