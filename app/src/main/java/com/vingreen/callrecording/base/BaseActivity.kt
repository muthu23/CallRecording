package com.vingreen.callrecording.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.vingreen.callrecording.R
import com.vingreen.callrecording.utils.ConnectivityLiveData
import com.vingreen.callrecording.utils.CustomLoaderDialog
import com.vingreen.callrecording.utils.LocaleUtils
import java.util.*

abstract class BaseActivity<VB : ViewDataBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : AppCompatActivity() {
    private var _binding: VB? = null
    val binding: VB
        get() = _binding as VB
    abstract val mLogTag: String

    private var mCustomLoader: CustomLoaderDialog? = null
    var connectivityLiveData: ConnectivityLiveData? = null
    lateinit var mNoInternetDialog: Dialog
    abstract fun setUp()


    protected fun log(value: String) {
        Log.v(mLogTag, value)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        mCustomLoader = CustomLoaderDialog(this, false)
        connectivityLiveData = ConnectivityLiveData(application)

        try {
            mNoInternetDialog = Dialog(this)
            mNoInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mNoInternetDialog.setCancelable(false)
            mNoInternetDialog.setContentView(R.layout.dialog_no_internet)
            mNoInternetDialog.findViewById<View>(R.id.tvSettings)
                .setOnClickListener {
                    startActivity(
                        Intent(Settings.ACTION_SETTINGS),
                        null
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        connectivityLiveData!!.observe(this) { it ->
            if (it) mNoInternetDialog.dismiss() else mNoInternetDialog.show() }

        setUp()
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleUtils.setLocale(newBase))
    }

    fun showLoading() {
        mCustomLoader?.let {
            Objects.requireNonNull(it.window)
                ?.setBackgroundDrawableResource(android.R.color.transparent)
            it.show()
        }
    }

    fun hideLoading() {
        mCustomLoader?.cancel()
    }

    fun showKeyboard() {
        val view = this.currentFocus
        view?.let {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            it.requestFocus()
            inputMethodManager.showSoftInput(it, 0)
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun openNewActivity(activity: AppCompatActivity, cls: Class<*>, finishCurrent: Boolean) {
        val intent = Intent(activity, cls)
        startActivity(intent)
        if (finishCurrent) activity.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
