package com.vingreen.callrecording.responses.login

data class TokenResponse(
    val access_token: String?,
    val refresh_token: String?
)