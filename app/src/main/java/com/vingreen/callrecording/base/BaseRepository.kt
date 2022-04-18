package com.vingreen.callrecording.base

import com.vingreen.callrecording.repository.SafeApiCall


abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}