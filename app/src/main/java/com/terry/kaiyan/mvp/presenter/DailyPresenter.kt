package com.terry.kaiyan.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.terry.kaiyan.mvp.contract.DailyContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import retrofit2.http.Url
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
class DailyPresenter
@Inject
constructor(model: DailyContract.Model, rootView: DailyContract.View) :
    BasePresenter<DailyContract.Model, DailyContract.View>(model, rootView) {
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

    private val bannerItemList:ArrayList<HomeBean.Issue.HomeItem> = ArrayList()

    fun getDailyFirstData() {
        mModel.getHomeBanner(1)
            .flatMap { homeBean ->
                val bannerList = homeBean.issueList[0].itemList
                bannerItemList.clear()
                bannerList.filter {
                    it.type =="video"
                }.forEach {
                    bannerItemList.add(it)
                }
                mModel.getMoreData(homeBean.nextPageUrl)
            }
            .subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(1,2))
            .doOnSubscribe(object : Consumer<Disposable>{
                override fun accept(t: Disposable?) {
                    mRootView.showLoading()
                }

            })
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                mRootView.hideLoading()
            }
            .subscribe(object : ErrorHandleSubscriber<HomeBean>(mErrorHandler){
                override fun onNext(t: HomeBean) {
                    mRootView.getHomeBannerSuccess(bannerItemList)
                    val tItemList = t.issueList[0].itemList
                    val newItemList = ArrayList<HomeBean.Issue.HomeItem>()
                    tItemList.filter {
                        it.type == "video" || it.type == "textHeader"
                    }.forEach {
                        newItemList.add(it)
                    }
                    mRootView.getHomeListSuccess(newItemList)
                }

            })
    }

    fun getDailyMoreData(url: String) {


    }
}
