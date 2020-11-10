package com.imassage.ui.utils

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

inline fun resetApplication(activity :Activity?){
    activity?.let{
        val i: Intent? = activity.baseContext.packageManager
            .getLaunchIntentForPackage(activity.baseContext.packageName)
        i?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(i)
        activity.finish()
    }

}