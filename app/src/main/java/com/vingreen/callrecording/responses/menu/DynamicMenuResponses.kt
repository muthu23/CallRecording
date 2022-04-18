package com.vingreen.callrecording.responses.menu

data class DynamicMenuResponses(
    val APIMessage: String,
    val APISuccess: String,
    val LeadStatus: List<LeadStatus>
)
data class LeadStatus(
    val LeadStatusId: String,
    val LeadStatusLeadsCount: String,
    val LeadStatusName: String,
    val LeadSubStatusList: List<LeadSubStatus>
)

data class LeadSubStatus(
    val LeadSubStatusId: String,
    val LeadSubStatusLeadsCount: String,
    val LeadSubStatusName: String
)