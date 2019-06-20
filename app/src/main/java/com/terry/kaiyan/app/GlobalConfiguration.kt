package com.terry.kaiyan.app

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.integration.ConfigModule

/**
 * Author:ChenXinming
 * Date:2019/06/20
 * Email:chenxinming@antelop.cloud
 * Description:
 */
class GlobalConfiguration : ConfigModule {
    override fun injectFragmentLifecycle(
        context: Context?,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>?
    ) {

    }

    override fun applyOptions(context: Context?, builder: GlobalConfigModule.Builder?) {
    }

    override fun injectAppLifecycle(context: Context?, lifecycles: MutableList<AppLifecycles>?) {
    }

    override fun injectActivityLifecycle(
        context: Context?,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>?
    ) {
    }
}
