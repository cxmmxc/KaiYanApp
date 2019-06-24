package com.terry.kaiyan.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import com.terry.kaiyan.api.ApiService
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.DailyContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.Observable
import javax.inject.Qualifier


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
class DailyModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), DailyContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun getHomeBanner(num: Int): Observable<HomeBean> {
        return mRepositoryManager
            .obtainRetrofitService(ApiService::class.java)
            .getBannerData(num)
    }

    override fun getMoreData(url: String): Observable<HomeBean> {
        return mRepositoryManager
            .obtainRetrofitService(ApiService::class.java)
            .getMoreData(url)
    }
}
