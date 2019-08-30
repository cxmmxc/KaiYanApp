package com.terry.kaiyan.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.terry.kaiyan.api.ApiService
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.HidePicContract
import com.terry.kaiyan.mvp.model.Bean.DouyinBeanBase
import io.reactivex.Observable


/**
 * Author:ChenXinming
 * Date: 2019/08/29
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@ActivityScope
class HidePicModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    HidePicContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun loadDouyinData(
        url:String,
        page: String?,
        VibratoId: String?,
        SortType: String?,
        Type:String?,
        sign: String?
    ): Observable<DouyinBeanBase> {
        return mRepositoryManager.obtainRetrofitService(ApiService::class.java)
            .getDouyinData(
                url,
                page,
                vibratoId = VibratoId,
                sortType = SortType,
                type = Type,
                sign = sign
            )
    }
}
