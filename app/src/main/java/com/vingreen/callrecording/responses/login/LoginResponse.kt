package com.vingreen.callrecording.responses.login

data class LoginResponse(
    val APISuccess: String,
    val APIMessage: String,
    val AccessLevelId: String,
    val AlternateEmailId: String,
    val AlternateMobileNumber: String,
    val AppAccId: String,
    val EmailId: String,
    val FirstName: String,
    val LastName: String,
    val MobileNumber: String,
    val Password: String,
    val ProfilePicture: String,
    val ReportingTo: String,
    val ServerKey: String,
    val UserId: String
)
