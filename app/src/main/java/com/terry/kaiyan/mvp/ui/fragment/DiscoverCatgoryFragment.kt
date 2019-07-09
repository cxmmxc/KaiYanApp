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

import com.terry.kaiyan.di.component.DaggerDiscoverCatgoryComponent
import com.terry.kaiyan.di.module.DiscoverCatgoryModule
import com.terry.kaiyan.mvp.contract.DiscoverCatgoryContract
import com.terry.kaiyan.mvp.presenter.DiscoverCatgoryPresenter

import com.terry.kaiyan.R


/**
 * Author:ChenXinming
 * Date: 2019/07/09
 * Email:chenxinming@antelop.cloud
 * Description:
 */


class DiscoverCatgoryFragment : BaseFragment<DiscoverCatgoryPresenter>(), DiscoverCatgoryContract.View {
    companion object {
        fun newInstance(): DiscoverCatgoryFragment {
            val fragment = DiscoverCatgoryFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDiscoverCatgoryComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .discoverCatgoryModule(DiscoverCatgoryModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_discover_catgory, container, false)
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
