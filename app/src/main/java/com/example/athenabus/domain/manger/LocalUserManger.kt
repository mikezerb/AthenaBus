package com.example.athenabus.domain.manger

import kotlinx.coroutines.flow.Flow


interface LocalUserManger {
    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>
}