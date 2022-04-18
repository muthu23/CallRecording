package com.vingreen.callrecording.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vingreen.callrecording.R
import com.vingreen.callrecording.utils.ConnectivityLiveData
import com.vingreen.callrecording.utils.CustomLoaderDialog
import java.util.*

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    private var mCustomLoader: CustomLoaderDialog? = null
    var connectivityLiveData: ConnectivityLiveData? = null
    lateinit var mNoInternetDialog: Dialog

    abstract fun setUp()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null)
            throw IllegalArgumentException("Binding cannot be null")

        mCustomLoader = CustomLoaderDialog(requireContext(), false)
        connectivityLiveData = ConnectivityLiveData(requireActivity().application)

        try {
            mNoInternetDialog = Dialog(requireContext())
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
        connectivityLiveData!!.observe(viewLifecycleOwner) { isInternetAvailable -> if (isInternetAvailable) mNoInternetDialog.dismiss() else mNoInternetDialog.show() }
        setUp()
        return binding.root
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
        val view = requireView()
        view.let {
            val inputMethodManager =
                requireContext().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            it.requestFocus()
            inputMethodManager.showSoftInput(it, 0)
        }
    }

    fun hideKeyboard() {
        val view = this.requireView()
        view?.let {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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