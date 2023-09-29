package com.example.athenabus.domain.repository

import com.example.athenabus.common.Resource
import com.example.athenabus.domain.model.Line
import kotlinx.coroutines.flow.Flow

interface BusLineRepository {
    fun getBusLines(): Flow<Resource<List<Line>>>
    fun getLineFromDB(searchTxt: String): Flow<Resource<List<Line>>>
    fun getLineCodesFromLineID(lineID: String): Flow<Resource<List<String>>>
    fun addFavoriteLines(Line: Line)
}