package com.vingreen.callrecording.view.fragment.leads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vingreen.callrecording.base.BaseViewModel
import com.vingreen.callrecording.repository.Repository
import com.vingreen.callrecording.responses.menu.LeadsMenuResponse
import com.vingreen.callrecording.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadsListViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel(repository) {

    private val _leadsResponse: MutableLiveData<NetworkResult<LeadsMenuResponse>> = MutableLiveData()
    val leadsResponse: LiveData<NetworkResult<LeadsMenuResponse>>
        get() = _leadsResponse


    fun getLeadsMenus(params: HashMap<String, Any>) {
        viewModelScope.launch(Dispatchers.IO) {
            _leadsResponse.value = NetworkResult.Loading
            _leadsResponse.value = repository.getLeadsMenus(params)
        }
    }


}