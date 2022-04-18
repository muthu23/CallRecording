package com.vingreen.callrecording.view.fragment.leads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vingreen.callrecording.base.BaseViewModel
import com.vingreen.callrecording.repository.Repository
import com.vingreen.callrecording.responses.leads.LeadsResponse
import com.vingreen.callrecording.responses.menu.LeadsMenuResponse
import com.vingreen.callrecording.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel(repository) {

    private val _leadsResponse: MutableLiveData<NetworkResult<LeadsResponse>> = MutableLiveData()
    private val _leadsFilteredResponse: MutableLiveData<NetworkResult<LeadsResponse>> = MutableLiveData()
    val leadsResponse: LiveData<NetworkResult<LeadsResponse>>
        get() = _leadsResponse

    val leadsFilteredResponse: LiveData<NetworkResult<LeadsResponse>>
        get() = _leadsFilteredResponse


    fun getAllLeads(params: HashMap<String, Any>) = viewModelScope.launch {
        _leadsResponse.value = NetworkResult.Loading
        _leadsResponse.value = repository.getLeads(params)
    }

    fun getLeads(params: HashMap<String, Any>) = viewModelScope.launch {
        _leadsFilteredResponse.value = NetworkResult.Loading
        _leadsFilteredResponse.value = repository.getLeads(params)
    }


}