package com.dicoding.storyapp.core.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun setLogin(user: UserEntity) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = user.userId
            preferences[NAME] = user.name
            preferences[TOKEN] = user.token
        }
    }

    fun getLogin(): Flow<UserEntity> {
        return dataStore.data.map { preferences ->
            UserEntity(
                preferences[USER_ID] ?: "",
                preferences[NAME] ?: "",
                preferences[TOKEN] ?: ""
            )
        }
    }

    suspend fun deleteLogin() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {

        private val USER_ID = stringPreferencesKey("userId")
        private val NAME = stringPreferencesKey("name")
        private val TOKEN = stringPreferencesKey("token")
    }
}