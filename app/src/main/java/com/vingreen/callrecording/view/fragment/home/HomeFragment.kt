package com.vingreen.callrecording.view.fragment.home

import androidx.navigation.Navigation
import com.vingreen.callrecording.R
import com.vingreen.callrecording.base.BaseFragment
import com.vingreen.callrecording.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun setUp() {

        binding.lnrLeads.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_nav_home_to_nav_leads)
        }
        binding.lnrTask.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_profile)

        }
        binding.lnrEnquiry.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_leads)

        }
        binding.lnrTimeline.setOnClickListener {

        }



    }



}