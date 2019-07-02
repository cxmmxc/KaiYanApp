package com.terry.kaiyan.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils

import com.terry.kaiyan.di.component.DaggerDicoverComponent
import com.terry.kaiyan.di.module.DicoverModule
import com.terry.kaiyan.mvp.contract.DicoverContract
import com.terry.kaiyan.mvp.presenter.DicoverPresenter

import com.terry.kaiyan.R
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */


class DiscoverFragment : BaseFragment<DicoverPresenter>(), DicoverContract.View {
    companion object {
        fun newInstance(): DiscoverFragment {
            val fragment = DiscoverFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDicoverComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .dicoverModule(DicoverModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_dicover, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        LogUtils.debugInfo("cxm", "DiscoverFragment")
    }
    suspend fun getDiscover():String{
        var okHttp = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        val request = Request.Builder()
                .url("http://baobab.kaiyanapp.com/api/v3/queries/hot")
                .get()
                .build()
        val response = okHttp.newCall(request).execute()
        LogUtils.debugInfo("cxm", "suspend --- ${response.body()?.string()}")
        return response.message()
    }

    override fun setData(data: Any?) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {

    }
}
