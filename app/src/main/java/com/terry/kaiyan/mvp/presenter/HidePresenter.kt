package com.terry.kaiyan.mvp.presenter

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.HideContract
import com.terry.kaiyan.mvp.model.Bean.DouyinBeanBase
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * Author:ChenXinming
 * Date: 2019/08/27
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class HidePresenter
@Inject
constructor(model: HideContract.Model, rootView: HideContract.View) :
    BasePresenter<HideContract.Model, HideContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy();
    }

    public fun loadDouyinData(page:String, VibratoId:String, SortType:String, sign:String) {
        mModel.loadDouyinData(page, VibratoId, SortType, sign)
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(1,2))
            .doOnSubscribe { mRootView.showLoading() }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                mRootView.hideLoading()
            }
            .`as`(
                AutoDispose.autoDisposable(
                    AndroidLifecycleScopeProvider.from(
                        mRootView as LifecycleOwner, Lifecycle.Event.ON_DESTROY
                    )))
            .subscribe(object : ErrorHandleSubscriber<DouyinBeanBase>(mErrorHandler){
                override fun onNext(t: DouyinBeanBase) {
                    mRootView.loadDouyinSuccess(t.Result?.data)

                }

            })
    }
}
