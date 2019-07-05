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

import com.terry.kaiyan.mvp.contract.VideoDetailContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
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
 * Date: 2019/07/04
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class VideoDetailPresenter
@Inject constructor(model: VideoDetailContract.Model, rootView: VideoDetailContract.View) :
    BasePresenter<VideoDetailContract.Model, VideoDetailContract.View>(model, rootView) {
    @Inject lateinit var mErrorHandler: RxErrorHandler
    @Inject lateinit var mApplication: Application
    @Inject lateinit var mImageLoader: ImageLoader
    @Inject lateinit var mAppManager: AppManager

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getPlayInfo(homeItem: HomeBean.Issue.HomeItem?) {
        mRootView.setVideoPlayUrl(homeItem?.data?.playUrl)
        mRootView.setRelatedBgUrl(homeItem?.data?.cover?.blurred)
        mRootView.setPlayInfo(homeItem)
    }

    fun getRelatedVideos(id: Long?) {
        mModel.getRelated(id)
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(1, 2))
            .doOnSubscribe { mRootView.showLoading() }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                mRootView.hideLoading()
            }
            .`as`(
                AutoDispose.autoDisposable(
                    AndroidLifecycleScopeProvider.from(
                        mRootView as LifecycleOwner, Lifecycle.Event.ON_DESTROY)))
            .subscribe(object : ErrorHandleSubscriber<HomeBean.Issue>(mErrorHandler) {
                override fun onNext(t: HomeBean.Issue) {
                    mRootView.getRelated(t)
                }

            })
    }
}
