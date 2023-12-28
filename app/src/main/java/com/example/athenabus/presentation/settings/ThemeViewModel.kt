package com.example.athenabus.presentation.settings

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athenabus.di.AppLanguage
import com.example.athenabus.di.DataStoreUtil
import com.example.athenabus.di.DataStoreUtil.Companion.APP_THEME_KEY
import com.example.athenabus.di.DataStoreUtil.Companion.IS_AMOLED_THEME_KEY
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
class ThemeViewModel @Inject constructor(
    dataStoreUtil: DataStoreUtil
) : ViewModel() {

    private val dataStore = dataStoreUtil.dataStore

    private val _themeState = MutableStateFlow(
        ThemeState(
            isAmoledMode = false,
            isDynamicMode = supportsDynamic(),
            appTheme = AppTheme.FOLLOW_SYSTEM
        )
    )
    val themeState: StateFlow<ThemeState> = _themeState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                ThemeState(
                    preferences[IS_AMOLED_THEME_KEY] ?: false,
                    preferences[IS_DYNAMIC_MODE_KEY] ?: supportsDynamic(),
                    preferences[APP_THEME_KEY]?.let { mapIntToAppTheme(it) } ?: AppTheme.FOLLOW_SYSTEM
                )
            }.collect {
                _themeState.value = it
            }
        }
    }

    private fun mapIntToAppTheme(themeValue: Int): AppTheme {
        return when (themeValue) {
            0 -> AppTheme.FOLLOW_SYSTEM
            1 -> AppTheme.LIGHT
            2 -> AppTheme.DARK
            else -> AppTheme.FOLLOW_SYSTEM
        }
    }

    fun setAmoledBlack() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { settings ->
                settings[IS_AMOLED_THEME_KEY] = !(settings[IS_AMOLED_THEME_KEY] ?: false)
            }
        }
    }

    fun setAppTheme(theme: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("setAppTheme", "theme: $theme")
            dataStore.edit { settings ->
                settings[APP_THEME_KEY] = theme
            }
        }
    }

    fun toggleDynamicColors() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[IS_DYNAMIC_MODE_KEY] =
                    !(preferences[IS_DYNAMIC_MODE_KEY] ?: supportsDynamic())
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
