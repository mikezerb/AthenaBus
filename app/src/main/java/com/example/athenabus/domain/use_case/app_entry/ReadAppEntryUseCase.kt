package com.example.athenabus.domain.use_case.app_entry

import com.example.athenabus.domain.manger.LocalUserManger
import kotlinx.coroutines.flow.Flow

class ReadAppEntryUseCase(
    private val localUserManger: LocalUserManger
) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManger.readAppEntry()
    }
}