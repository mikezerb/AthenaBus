package com.example.athenabus.presentation.settings

import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.di.AppLanguage
import com.example.athenabus.di.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    dataStoreUtil: DataStoreUtil
) : ViewModel() {
    private val dataStore = dataStoreUtil.dataStore

    private val _settingState = MutableStateFlow(
        SettingsState(
            lanCode = determineLanCode()
        )
    )

    val settingState: StateFlow<SettingsState> = _settingState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                SettingsState(
                    preferences[DataStoreUtil.SELECTED_LANGUAGE_CODE] ?: determineLanCode()
                )
            }.collect {
                _settingState.value = it
            }
        }
    }

    fun saveSelectedLang(appLang: AppLanguage) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[DataStoreUtil.SELECTED_LANGUAGE_CODE] = appLang.selectedLangCode
            }
        }
    }
}