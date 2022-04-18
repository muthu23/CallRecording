package com.vingreen.callrecording.responses.leads

data class LeadsResponse(
    val APIMessage: String,
    val APISuccess: String,
    val DropDownDepartmentId: String,
    val DropDownDepartmentsList: List<DropDownDepartments>,
    val DropDownUserId: String,
    val DropDownUsersList: List<DropDownUsers>,
    val Leads: List<Lead> = emptyList()
)

data class DropDownDepartments(
    val Id: String,
    val Name: String
)

data class DropDownUsers(
    val Id: String,
    val Name: String
)

data class Lead(
    val Age: String,
    val AlterEmailId: String,
    val AlterMobileNumber: String,
    val CreatedAt: String,
    val EmailId: String,
    val LeadId: String,
    val LeadName: String,
    val MobileNumber: String,
    val ProductName: String,
    val Qualification: String,
    val UpdatedAt: String
)
