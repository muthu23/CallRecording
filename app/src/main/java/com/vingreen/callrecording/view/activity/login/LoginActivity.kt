package com.vingreen.callrecording.view.activity.login

import android.Manifest
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.aykuttasil.callrecord.CallRecord
import com.permissionx.guolindev.PermissionX
import com.vingreen.callrecording.R
import com.vingreen.callrecording.admin.DeviceAdmin
import com.vingreen.callrecording.base.BaseActivity
import com.vingreen.callrecording.databinding.ActivityLoginBinding
import com.vingreen.callrecording.utils.NetworkResult
import com.vingreen.callrecording.utils.ViewUtils.preferenceHelper
import com.vingreen.callrecording.utils.setValue
import com.vingreen.callrecording.view.activity.home.HomeActivity
import com.vingreen.callrecording.view.activity.resetpassword.ResetPasswordActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private lateinit var callRecord: CallRecord
    private val REQUEST_CODE = 0
    private var mDPM: DevicePolicyManager? = null
    private var mAdminName: ComponentName? = null

    override val mLogTag: String get() = LoginActivity::class.toString()

    private val viewModel: LoginViewModel by viewModels()

    override fun setUp() {
        val animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.bottom_to_original
        )
        binding.parentLayout.animation = animation

        binding.forgotPassword.setOnClickListener {
            openNewActivity(this, ResetPasswordActivity::class.java, false)
        }

        binding.login.setOnClickListener {
            val loginRequest = HashMap<String, Any>()
            loginRequest["Email"] = binding.userNameInputLayout.editText?.text.toString()
            loginRequest["Password"] = binding.passwordInputLayout.editText?.text.toString()
            viewModel.login(loginRequest)
        }


        setupPermission()
        setupObserver()
        setupDevicePolicy()

    }

    private fun setupPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            )
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    startRecord()
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                    openNewActivity(this@LoginActivity, HomeActivity::class.java,true)

                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun setupDevicePolicy() {
        try {
            mDPM = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
            mAdminName = ComponentName(this, DeviceAdmin::class.java)
            if (!mDPM!!.isAdminActive(mAdminName!!)) {
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName)
                intent.putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Click on Activate button to secure your application."
                )
                startActivityForResult(intent, REQUEST_CODE)
            } else {
                // mDPM.lockNow();
                // Intent intent = new Intent(MainActivity.this,
                // TrackDeviceService.class);
                // startService(intent);
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE == requestCode) {
            setupPermission()
        }
    }

    private fun startRecord() {
        callRecord = CallRecord.Builder(this)
            .setLogEnable(true)
            .setRecordFileName("RecordFileName")
            .setRecordDirName("RecordDirName")
            .setRecordDirPath(this.externalCacheDir?.path) // optional & default value
            .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
            .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB) // optional & default value
            .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION) // optional & default value
            .setShowSeed(true) // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
            .build()
        callRecord.enableSaveFile()
        callRecord.startCallReceiver()
    }

    private fun setupObserver() {
        viewModel.loginResponse.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoading()
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.value.FirstName,
                            it.value.LastName
                        )
                        preferenceHelper.setValue("ID",it.value.UserId)
                        openNewActivity(this@LoginActivity,HomeActivity::class.java,true)
                    }
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

    override fun onDestroy() {
        super.onDestroy()
        callRecord.stopCallReceiver()
    }
}