package com.example.athenabus.domain.use_case.app_entry

import com.example.athenabus.domain.manger.LocalUserManger

class SaveAppEntryUseCase(
    private val localUserManger: LocalUserManger
) {

    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }
}