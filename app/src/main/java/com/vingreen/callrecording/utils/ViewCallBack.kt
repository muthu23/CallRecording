package com.vingreen.callrecording.utils

import android.content.DialogInterface
import com.vingreen.callrecording.responses.menu.LeadStatus

interface ViewCallBack {

    interface Alert {
        fun onPositiveButtonClick(dialog: DialogInterface)
        fun onNegativeButtonClick(dialog: DialogInterface)
    }
    interface AlertWithOk {
        fun onPositiveButtonClick(dialog: DialogInterface)
    }

    interface LeadsItemClick {
        fun onItemClick(item: LeadStatus)
    }
}