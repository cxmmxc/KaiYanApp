package com.terry.kaiyan.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.api.ApiService
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.VideoDetailContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean.Issue
import io.reactivex.Observable

/**
 * Author:ChenXinming
 * Date: 2019/07/04
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class VideoDetailModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), VideoDetailContract.Model {
    override fun getRelated(id: Long?): Observable<Issue> {
        return mRepositoryManager
            .obtainRetrofitService(ApiService::class.java)
            .getRelated(id)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy()
    }
}
