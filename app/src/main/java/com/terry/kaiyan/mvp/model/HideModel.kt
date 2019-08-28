package com.terry.kaiyan.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.api.ApiService
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.HideContract
import com.terry.kaiyan.mvp.model.Bean.DouyinBeanBase
import io.reactivex.Observable


/**
 * Author:ChenXinming
 * Date: 2019/08/27
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class HideModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    HideContract.Model {



    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun loadDouyinData(
        page: String,
        VibratoId: String,
        SortType: String,
        sign: String
    ): Observable<DouyinBeanBase> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .getDouyinData(
                "https://api.wxzslm.com/api/Vibrato/LoadData",
                page,
                vibratoId = VibratoId,
                sortType = SortType,
                sign = sign
            )
    }
}
