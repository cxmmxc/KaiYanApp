package com.terry.kaiyan.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.terry.kaiyan.di.component.DaggerDailyComponent
import com.terry.kaiyan.di.module.DailyModule
import com.terry.kaiyan.mvp.contract.DailyContract
import com.terry.kaiyan.mvp.presenter.DailyPresenter

import com.terry.kaiyan.R


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
class DailyFragment : BaseFragment<DailyPresenter>(), DailyContract.View {
    companion object {
        fun newInstance(): DailyFragment {
            val fragment = DailyFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDailyComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .dailyModule(DailyModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

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
