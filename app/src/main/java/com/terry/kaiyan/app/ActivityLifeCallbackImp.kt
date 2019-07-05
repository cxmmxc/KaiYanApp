package com.terry.kaiyan.app

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.ui.activity.VideoDetailActivity
import com.terry.kaiyan.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.fragment_daily.toolbar

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
        if (activity is VideoDetailActivity) {
            var rootDecorView = activity?.findViewById<View>(R.id.videoPlayer)
            expandToolbar(rootDecorView, activity)
        }
    }

    private fun expandToolbar(view: View?, activity: Activity?) {
        view?.let {
            var statusBarHeight = getStatusBarHeight(activity)
            val lp = it.layoutParams
            if (lp != null && lp.height > 0) {
                lp.height += statusBarHeight
            }
            it.setPadding(
                0,
                it.paddingTop + statusBarHeight,
                0,
                0
            )
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
