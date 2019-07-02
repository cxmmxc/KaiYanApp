package com.terry.kaiyan.mvp.presenter

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.terry.kaiyan.mvp.contract.SearchContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import javax.inject.Inject


/**
 * Author:ChenXinming
 * Date: 2019/06/25
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class SearchPresenter
@Inject
constructor(model: SearchContract.Model, rootView: SearchContract.View) :
        BasePresenter<SearchContract.Model, SearchContract.View>(model, rootView) {
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

    var netPageUrl: String? = null

    fun getHotWords() {
        mModel.getHotWords()
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(1, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .`as`(AutoDispose.autoDisposable(
                        AndroidLifecycleScopeProvider.from(
                                mRootView as LifecycleOwner, Lifecycle.Event.ON_DESTROY
                        )))
                .subscribe(object : ErrorHandleSubscriber<ArrayList<String>>(mErrorHandler) {
                    override fun onNext(t: ArrayList<String>) {
                        mRootView.getHotWordsSuccess(t)
                    }

                })
    }

    fun getHotDatas(keywords: String) {
        mModel.getHotDataWords(keywords)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(1, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .`as`(AutoDispose.autoDisposable(
                        AndroidLifecycleScopeProvider.from(
                                mRootView as LifecycleOwner, Lifecycle.Event.ON_DESTROY
                        )))
                .subscribe(object : ErrorHandleSubscriber<HomeBean.Issue>(mErrorHandler) {
                    override fun onNext(t: HomeBean.Issue) {
                        mRootView.getHotDatasSuccess(t)
                        netPageUrl = t.nextPageUrl
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.getHotDatasFail()
                    }

                })
    }

    fun getMoreHotDatas() {
        netPageUrl?.let {
            mModel.getMoreHotDataWords(it)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(RetryWithDelay(1, 2))
                    .observeOn(AndroidSchedulers.mainThread())
                    .`as`(AutoDispose.autoDisposable(
                            AndroidLifecycleScopeProvider.from(
                                    mRootView as LifecycleOwner, Lifecycle.Event.ON_DESTROY
                            )))
                    .subscribe(object : ErrorHandleSubscriber<HomeBean.Issue>(mErrorHandler) {
                        override fun onNext(t: HomeBean.Issue) {
                            mRootView.getMoreHotDatasSuccess(t)
                            netPageUrl = t.nextPageUrl
                        }

                        override fun onError(t: Throwable) {
                            super.onError(t)
                            mRootView.getMoreHotDatasFail()
                        }

                    })
        }

    }
}
