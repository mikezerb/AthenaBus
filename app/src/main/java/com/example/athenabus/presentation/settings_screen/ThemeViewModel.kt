package com.example.athenabus.presentation.settings_screen

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.di.AppLanguage
import com.example.athenabus.di.DataStoreUtil
import com.example.athenabus.di.DataStoreUtil.Companion.IS_DARK_MODE_KEY
import com.example.athenabus.di.DataStoreUtil.Companion.IS_DYNAMIC_MODE_KEY
import com.example.athenabus.di.DataStoreUtil.Companion.SELECTED_LANGUAGE
import com.example.athenabus.di.DataStoreUtil.Companion.SELECTED_LANGUAGE_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ThemeViewModel @Inject constructor(dataStoreUtil: DataStoreUtil) : ViewModel() {

    private val _themeState = MutableStateFlow(ThemeState(false))
    val themeState: StateFlow<ThemeState> = _themeState
    private val _dynamicState = MutableStateFlow(DynamicState(supportsDynamic()))
    val dynamicState: StateFlow<DynamicState> = _dynamicState

    private val _langState = MutableStateFlow(LanguageState("Greek", "el"))
    val langState: StateFlow<LanguageState> = _langState

    private val dataStore = dataStoreUtil.dataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                ThemeState(preferences[IS_DARK_MODE_KEY] ?: false)
            }.collect {
                _themeState.value = it
            }
            dataStore.data.map { preferences ->
                DynamicState(preferences[IS_DYNAMIC_MODE_KEY] ?: true)
            }.collect {
                _dynamicState.value = it
            }
            dataStore.data.map { preferences ->
                LanguageState(
                    preferences[SELECTED_LANGUAGE] ?: "Greek",
                    preferences[SELECTED_LANGUAGE_CODE] ?: "el"
                )
            }.collect {
                _langState.value = it
            }
        }

    }

    fun toggleTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[IS_DARK_MODE_KEY] = !(preferences[IS_DARK_MODE_KEY] ?: false)
            }
        }
    }

    fun toggleDynamicColors() {
        viewModelScope.launch {
            Log.d("dynamic", "Try to toggle dynamic color")
            dataStore.edit { preferences ->
                Log.d("dynamic", "Dynamic preference is: " + preferences[IS_DYNAMIC_MODE_KEY])
                preferences[IS_DYNAMIC_MODE_KEY] = !(preferences[IS_DYNAMIC_MODE_KEY] ?: false)
            }
        }
    }

    fun saveSelectedLang(appLang: AppLanguage) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("lang", "Try to change lang")
            dataStore.edit { preferences ->
                Log.d("lang", "lang is: " + preferences[SELECTED_LANGUAGE])
                preferences[SELECTED_LANGUAGE] = appLang.selectedLang
                preferences[SELECTED_LANGUAGE_CODE] = appLang.selectedLangCode

            }
        }
    }

}