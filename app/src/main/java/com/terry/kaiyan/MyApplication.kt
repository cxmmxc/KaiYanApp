package com.terry.kaiyan

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.jess.arms.base.BaseApplication

/**
 * Author:ChenXinming
 * Date:2019/06/20
 * Email:chenxinming@antelop.cloud
 * Description:
 */
open class MyApplication :BaseApplication(){

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    val lifecycleCallbacks : ActivityLifecycleCallbacks = object  : ActivityLifecycleCallbacks{
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
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
}
