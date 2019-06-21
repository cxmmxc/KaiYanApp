package com.terry.kaiyan.app

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager

/**
 * Author:ChenXinming
 * Date:2019/06/21
 * Description:
 */
class ActivityLifeCallbackImp :Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.or(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity?.window?.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}
