package com.terry.kaiyan.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.terry.kaiyan.di.component.DaggerDailyComponent
import com.terry.kaiyan.di.module.DailyModule
import com.terry.kaiyan.mvp.contract.DailyContract
import com.terry.kaiyan.mvp.presenter.DailyPresenter

import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.mvp.ui.adapter.DailyBannerAdapter
import com.terry.kaiyan.mvp.ui.adapter.DailyHomeAdapter
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.item_daily_banner.view.*


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
class DailyFragment : BaseFragment<DailyPresenter>(), DailyContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var mAdapter:DailyHomeAdapter ?= null
    private lateinit var layoutManager:RecyclerView.LayoutManager
    private lateinit var bannerAdapter:DailyBannerAdapter

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
        dailySwipeLayout.setOnRefreshListener(this)
        mAdapter = DailyHomeAdapter(context!!)
        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        dailyRecyclerView.layoutManager = layoutManager
        dailyRecyclerView.itemAnimator = DefaultItemAnimator()
        dailyRecyclerView.adapter = mAdapter
        inflateHeader()
        dailyRecyclerView.postDelayed({ onRefresh() }, 150)
    }

    private fun inflateHeader() {
        val view:View = layoutInflater.inflate(R.layout.item_daily_banner,null)
        bannerAdapter = DailyBannerAdapter(activity!!)
        view.dailyViewPager.adapter = bannerAdapter
        view.dailyViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mAdapter?.addHeaderView(view)
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

    override fun onRefresh() {
        dailySwipeLayout.isRefreshing =true
        mPresenter?.getDailyFirstData()
    }
    override fun getHomeBannerFail() {
        dailySwipeLayout.isRefreshing = false
    }

    override fun getHomeBannerSuccess(homeBean: ArrayList<HomeBean.Issue.HomeItem>?) {
        dailySwipeLayout.isRefreshing = false
        bannerAdapter.setNewData(homeBean)
        bannerAdapter.notifyDataSetChanged()
    }

    override fun getHomeListSuccess(homeBean: ArrayList<HomeBean.Issue.HomeItem>?) {
        mAdapter?.addData(homeBean!!)
    }

    override fun getHomeListFail() {

    }
}
