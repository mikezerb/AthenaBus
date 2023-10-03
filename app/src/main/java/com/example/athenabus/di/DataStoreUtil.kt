package com.example.athenabus.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.athenabus.common.Constants
import javax.inject.Inject

class DataStoreUtil @Inject constructor(context: Context) {

    val dataStore = context.dataStore

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.APP_SETTINGS)
        val IS_DARK_MODE_KEY = booleanPreferencesKey(Constants.DARK_MODE)
    }
}