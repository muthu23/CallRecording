package com.vingreen.callrecording.view.fragment.leads

import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vingreen.callrecording.R
import com.vingreen.callrecording.base.BaseFragment
import com.vingreen.callrecording.databinding.FragmentLeadsViewBinding
import com.vingreen.callrecording.responses.leads.DropDownDepartments
import com.vingreen.callrecording.responses.leads.DropDownUsers
import com.vingreen.callrecording.responses.leads.Lead
import com.vingreen.callrecording.utils.NetworkResult
import com.vingreen.callrecording.utils.ViewUtils
import com.vingreen.callrecording.utils.getValue
import com.vingreen.callrecording.view.adapter.LeadsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import org.angmarch.views.OnSpinnerItemSelectedListener


@AndroidEntryPoint
class LeadsViewFragment :
    BaseFragment<FragmentLeadsViewBinding>(FragmentLeadsViewBinding::inflate) {

    private val viewModel: LeadsViewModel by viewModels()

    private lateinit var dropDownUsersList: List<DropDownUsers>
    private lateinit var dropDownDepartmentsList: List<DropDownDepartments>
    override fun setUp() {
        getAllLeads()
        setupObserver()
        setupListener()
    }

    private fun setupListener() {
        binding.leadsSpinner.onSpinnerItemSelectedListener =
            OnSpinnerItemSelectedListener { _, _, _, _ -> getFilteredLeads() }
        binding.departSpinner.onSpinnerItemSelectedListener =
            OnSpinnerItemSelectedListener { _, _, _, _ -> getFilteredLeads() }
    }

    private fun getAllLeads() {
        val loginRequest = HashMap<String, Any>()

        loginRequest["LeadStatus"] = "All"
        loginRequest["LeadSubStatus"] = "All"
        loginRequest["DataCount"] = "10"
        loginRequest["SearchTag"] = ""
        loginRequest["PageNumber"] = "1"
        loginRequest["DropDownUserId"] = "All"
        loginRequest["DropDownDepartmentId"] = "All"
        loginRequest["UserId"] = ViewUtils.preferenceHelper.getValue("ID", "1").toString()
        viewModel.getAllLeads(loginRequest)

    }


    private fun getFilteredLeads() {
        val loginRequest = HashMap<String, Any>()
        val dropDownUserId: Any
        val dropDownDepartmentId: Any

        dropDownUserId = if (binding.leadsSpinner.selectedIndex != 0)
            dropDownUsersList[binding.leadsSpinner.selectedIndex].Id
        else "All"
        dropDownDepartmentId = if (binding.departSpinner.selectedIndex != 0)
            dropDownDepartmentsList[binding.departSpinner.selectedIndex].Id
        else "All"

        loginRequest["LeadStatus"] = "All"
        loginRequest["LeadSubStatus"] = "All"
        loginRequest["DataCount"] = "10"
        loginRequest["SearchTag"] = ""
        loginRequest["PageNumber"] = "1"
        loginRequest["DropDownUserId"] = dropDownUserId
        loginRequest["DropDownDepartmentId"] = dropDownDepartmentId
        loginRequest["UserId"] = ViewUtils.preferenceHelper.getValue("ID", "1").toString()
        viewModel.getLeads(loginRequest)

    }

    private fun setupObserver() {

        viewModel.leadsResponse.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoading()
                    loadDepartmentSpinner(it.value.DropDownDepartmentsList)
                    loadLeadsSpinner(it.value.DropDownUsersList)
                    if (it.value.APIMessage != "No Leads Found")
                        loadLeadsRecycler(it.value.Leads)
                    else ViewUtils.showToast(requireContext(), it.value.APIMessage, false)


                }
                is NetworkResult.Failure -> {
                    hideLoading()
                }
                else -> {
                    showLoading()
                }
            }
        }


        viewModel.leadsFilteredResponse.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoading()
                    if (it.value.APIMessage != "No Leads Found")
                        loadLeadsRecycler(it.value.Leads)
                    else ViewUtils.showToast(requireContext(), it.value.APIMessage, false)

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

    private fun loadLeadsRecycler(leads: List<Lead>) {
        val leadAdapter = LeadsListAdapter(requireContext(), leads)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = leadAdapter
    }

    private fun loadLeadsSpinner(dropDownUsersList: List<DropDownUsers>) {
        this.dropDownUsersList = dropDownUsersList
        val leadsUsers = ArrayList<String>()
        dropDownUsersList.forEach {
            if (it.Name == "All")
                leadsUsers.add(0, it.Name)
            else
                leadsUsers.add(it.Name)
        }
        binding.leadsSpinner.background =
            ResourcesCompat.getDrawable(resources, R.drawable.bg_spinner, requireActivity().theme)
        binding.leadsSpinner.attachDataSource(leadsUsers)
    }

    private fun loadDepartmentSpinner(dropDownDepartmentsList: List<DropDownDepartments>) {
        this.dropDownDepartmentsList = dropDownDepartmentsList

        val departments = ArrayList<String>()
        dropDownDepartmentsList.forEach {
            if (it.Name == "All")
                departments.add(0, it.Name)
            else
                departments.add(it.Name)
        }
        binding.departSpinner.background =
            ResourcesCompat.getDrawable(resources, R.drawable.bg_spinner, requireActivity().theme)
        binding.departSpinner.attachDataSource(departments)
    }

}