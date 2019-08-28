package com.terry.kaiyan.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils

import com.terry.kaiyan.di.component.DaggerDicoverComponent
import com.terry.kaiyan.di.module.DicoverModule
import com.terry.kaiyan.mvp.contract.DicoverContract
import com.terry.kaiyan.mvp.presenter.DicoverPresenter

import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.ui.adapter.DiscoverMainAdapter
import com.terry.kaiyan.utils.TabLayoutMediator
import com.terry.kaiyan.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.fragment_dicover.*


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */


class DiscoverFragment : BaseFragment<DicoverPresenter>(), DicoverContract.View {

    private var mAdapter: DiscoverMainAdapter? = null

    companion object {
        fun newInstance(): DiscoverFragment {
            return DiscoverFragment()
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
        activity.let {
            var statusBarHeight = getStatusBarHeight(context)
            val lp = titleDiscoverTv?.layoutParams
            if (lp != null && lp.height > 0) {
                lp.height += statusBarHeight
            }
            titleDiscoverTv.setPadding(
                    0,
                    titleDiscoverTv.paddingTop + statusBarHeight,
                    0,
                    titleDiscoverTv.paddingBottom
            )
        }
        mAdapter = DiscoverMainAdapter(activity!!)
        discoverViewPager2.adapter = mAdapter
        TabLayoutMediator(discoverTabLayout, discoverViewPager2, true, object : TabLayoutMediator.OnConfigureTabCallback {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                when (position) {
                    0 -> {
                        tab.text = "关注"
                    }
                    1 -> {
                        tab.text = "分类"
                    }
                }
            }
        }).attach()
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

    override fun onResume() {
        super.onResume()
        LogUtils.debugInfo("cxm", "DiscoverFragment onResume")
    }
}
