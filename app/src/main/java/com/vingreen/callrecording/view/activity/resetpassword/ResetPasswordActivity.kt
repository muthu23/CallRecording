package com.vingreen.callrecording.view.activity.resetpassword

import com.vingreen.callrecording.base.BaseActivity
import com.vingreen.callrecording.databinding.ActivityResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordActivity : BaseActivity<ActivityResetPasswordBinding>(ActivityResetPasswordBinding::inflate) {

    override val mLogTag: String
        get() = ResetPasswordActivity::class.java.toString()

    override fun setUp() {


    }


}