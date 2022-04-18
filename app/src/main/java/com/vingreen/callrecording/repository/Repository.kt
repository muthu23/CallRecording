package com.vingreen.callrecording.repository

import com.vingreen.callrecording.base.BaseRepository
import com.vingreen.callrecording.utils.UserPreferences
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: APIServices,
    private val preferences: UserPreferences
) : BaseRepository(api) {


    suspend fun login(params: HashMap<String, Any>) = safeApiCall { api.login(params) }


    suspend fun getLeadsMenus(params: HashMap<String, Any>) = safeApiCall { api.getLeadsMenus(params) }

    suspend fun getLeads(params: HashMap<String, Any>) = safeApiCall { api.getLeads(params) }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        preferences.saveAccessTokens(accessToken, refreshToken)
    }

}