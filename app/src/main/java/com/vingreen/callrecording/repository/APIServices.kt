package com.vingreen.callrecording.repository

import com.vingreen.callrecording.base.BaseApi
import com.vingreen.callrecording.responses.leads.LeadsResponse
import com.vingreen.callrecording.responses.menu.LeadsMenuResponse
import com.vingreen.callrecording.responses.login.LoginResponse
import retrofit2.http.*

interface APIServices : BaseApi {

    @FormUrlEncoded
    @POST("api/mobile/login.php")
    suspend fun login(@FieldMap params: HashMap<String, Any> ): LoginResponse

    @GET("api/mobile/sidebar_lead_stages_list.php?")
    suspend fun getLeadsMenus(@QueryMap params: HashMap<String, Any> ): LeadsMenuResponse

    @GET("api/mobile/leads.php?")
    suspend fun getLeads(@QueryMap params: HashMap<String, Any> ): LeadsResponse
}