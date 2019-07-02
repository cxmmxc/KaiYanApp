package com.terry.kaiyan.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.api.ApiService
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.SearchContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.Observable


/**
 * Author:ChenXinming
 * Date: 2019/06/25
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class SearchModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SearchContract.Model {


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getHotWords(): Observable<ArrayList<String>> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
                .getHotWords()
    }

    override fun getHotDataWords(keywords: String): Observable<HomeBean.Issue> {
        return mRepositoryManager
                .obtainRetrofitService(ApiService::class.java)
                .getSearchHots(10,10, keywords)
    }

    override fun getMoreHotDataWords(url: String): Observable<HomeBean.Issue> {
        return  mRepositoryManager
                .obtainRetrofitService(ApiService::class.java)
                .getSearchMoreHots(url)
    }
}
