package com.terry.kaiyan.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.api.ApiService
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.HomeContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.Observable


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class HomeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model {
    override fun getHomeBanner(num: Int): Observable<HomeBean> {
        return mRepositoryManager
            .obtainRetrofitService(ApiService::class.java)
            .getBannerData(num)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy()
    }
}
