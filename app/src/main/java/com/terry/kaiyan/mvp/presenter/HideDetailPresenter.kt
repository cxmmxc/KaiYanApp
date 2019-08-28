package com.terry.kaiyan.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.HideDetailContract


/**
 * Author:ChenXinming
 * Date: 2019/08/28
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class HideDetailPresenter
@Inject
constructor(model: HideDetailContract.Model, rootView: HideDetailContract.View) :
        BasePresenter<HideDetailContract.Model, HideDetailContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy()
    }
}
