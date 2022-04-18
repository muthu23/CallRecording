package com.vingreen.callrecording.view.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vingreen.callrecording.base.BaseViewModel
import com.vingreen.callrecording.repository.Repository
import com.vingreen.callrecording.responses.login.LoginResponse
import com.vingreen.callrecording.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<NetworkResult<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<NetworkResult<LoginResponse>>
        get() = _loginResponse


    fun login(params: HashMap<String, Any>) = viewModelScope.launch {
        _loginResponse.value = NetworkResult.Loading
        _loginResponse.value = repository.login(params)
    }


    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        repository.saveAccessTokens(accessToken, refreshToken)
    }
}