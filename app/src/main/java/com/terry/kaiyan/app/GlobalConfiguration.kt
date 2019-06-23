package com.terry.kaiyan.app

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy
import com.jess.arms.http.log.RequestInterceptor
import com.jess.arms.integration.ConfigModule
import com.terry.kaiyan.BuildConfig
import com.terry.kaiyan.api.UrlConstant

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
        if (!BuildConfig.LOG_DEBUG) {
            builder?.let {
                it.printHttpLogLevel(RequestInterceptor.Level.NONE)
            }
        }
        builder?.let {
            it.baseurl(UrlConstant.BASE_URL)
                .imageLoaderStrategy(GlideImageLoaderStrategy())
                .gsonConfiguration { context, builder ->
                    builder.serializeNulls()
                        .setPrettyPrinting()
                }
                .build()

        }
    }

    override fun injectAppLifecycle(context: Context?, lifecycles: MutableList<AppLifecycles>?) {
    }

    override fun injectActivityLifecycle(
        context: Context?,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>?
    ) {
        lifecycles?.let {
            it.add(ActivityLifeCallbackImp())
        }
    }
}
