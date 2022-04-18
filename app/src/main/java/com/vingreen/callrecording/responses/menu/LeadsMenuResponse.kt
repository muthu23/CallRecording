package com.vingreen.callrecording.responses.menu


data class LeadsMenuResponse(
    val title: String,
    val LeadStatus: List<LeadStatus>
)