package com.terry.kaiyan.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.terry.kaiyan.mvp.contract.HotContract


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
@FragmentScope
class HotModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HotContract.Model {
    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
