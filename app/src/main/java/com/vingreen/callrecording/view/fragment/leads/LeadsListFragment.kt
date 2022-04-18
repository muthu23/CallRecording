package com.vingreen.callrecording.view.fragment.leads

import android.widget.ExpandableListView.OnGroupExpandListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.vingreen.callrecording.R
import com.vingreen.callrecording.base.BaseFragment
import com.vingreen.callrecording.databinding.FragmentLeadListBinding
import com.vingreen.callrecording.responses.menu.LeadStatus
import com.vingreen.callrecording.utils.NetworkResult
import com.vingreen.callrecording.utils.ViewCallBack
import com.vingreen.callrecording.utils.ViewUtils.preferenceHelper
import com.vingreen.callrecording.utils.getValue
import com.vingreen.callrecording.view.adapter.LeadsExpandListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeadsListFragment : BaseFragment<FragmentLeadListBinding>(FragmentLeadListBinding::inflate),
    ViewCallBack.LeadsItemClick {

    private val viewModel: LeadsListViewModel by viewModels()
    private lateinit var adapter: LeadsExpandListAdapter


    override fun setUp() {

        val loginRequest = HashMap<String, Any>()
        loginRequest["dropDownDeptId"] = "1"
        loginRequest["dropDownUserId"] = "All"
        loginRequest["userId"] = preferenceHelper.getValue("ID", "1").toString()
        viewModel.getLeadsMenus(loginRequest)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.leadsResponse.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoading()
                    loadLeadListMenus(it.value.LeadStatus)
                }
                is NetworkResult.Failure -> {
                    hideLoading()
                }
                else -> {
                    showLoading()
                }
            }
        }
    }

    private fun loadLeadListMenus(leadStatus: List<LeadStatus>) {
        adapter = LeadsExpandListAdapter(requireContext(), leadStatus.toMutableList(), this)
        binding.leadsExpandable.setAdapter(adapter)

        binding.leadsExpandable.setOnGroupExpandListener(object : OnGroupExpandListener {
            var previousGroup = -1
            override fun onGroupExpand(groupPosition: Int) {
                if (groupPosition != previousGroup) {
                    binding.leadsExpandable.collapseGroup(previousGroup)
                    adapter.highlightGroup(groupPosition)
                }
                previousGroup = groupPosition
            }
        })


    }

    override fun onItemClick(item: LeadStatus) {
        Navigation.findNavController(binding.root).navigate(R.id.action_nav_leads_to_nav_leads_view)
    }


}