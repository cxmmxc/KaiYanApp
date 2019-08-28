package com.terry.kaiyan.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.terry.kaiyan.di.component.DaggerHideComponent
import com.terry.kaiyan.di.module.HideModule
import com.terry.kaiyan.mvp.contract.HideContract
import com.terry.kaiyan.mvp.presenter.HidePresenter

import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.DouyinBeanBase
import com.terry.kaiyan.mvp.ui.adapter.HideAdapter
import com.terry.kaiyan.utils.paramsToSign
import kotlinx.android.synthetic.main.activity_hide.*


/**
 * Author:ChenXinming
 * Date: 2019/08/27
 * Email:chenxinming@antelop.cloud
 * Description:
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class HideActivity : BaseActivity<HidePresenter>(), HideContract.View,
    SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private lateinit var mAdapter: HideAdapter
    private var mPage = 1

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHideComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .hideModule(HideModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_hide //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        hideRefresh.setOnRefreshListener(this)
        hideRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        hideRecyclerView.itemAnimator = DefaultItemAnimator()
        mAdapter = HideAdapter()
        hideRecyclerView.adapter = mAdapter
        mAdapter.setOnLoadMoreListener(this, hideRecyclerView)
        hideRecyclerView.postDelayed({
            hideRefresh.isRefreshing = true
            getData(mPage)
        },100)
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val douyinBean = adapter.getItem(position) as DouyinBeanBase.ResultBean.DouyinBean
            val intent = Intent(this@HideActivity, HideDetailActivity::class.java)
            intent.putExtra("url",douyinBean.Video)
            intent.putExtra("cover",douyinBean.VideoImg)
            startActivity(intent)
        }
    }
    override fun onRefresh() {
        mPage = 1
        getData(mPage)
    }

    override fun onLoadMoreRequested() {
        mPage++
        getData(mPage)
    }

    private fun getData(page:Int) {
        val hashMap = HashMap<String, String>()
        hashMap["Page"] = page.toString()
        hashMap["VibratoId"] = "0"
        hashMap["SortType"] = "2"

        mPresenter?.loadDouyinData(hashMap["Page"]!!,hashMap["VibratoId"]!!, hashMap["SortType"]!!, paramsToSign(hashMap))
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
        finish()
    }
    override fun loadDouyinSuccess(list: List<DouyinBeanBase.ResultBean.DouyinBean>?) {
        hideRefresh.isRefreshing = false
        if (mPage == 1) {
            mAdapter.setNewData(list)
        } else {
            mAdapter.addData(list!!)
            if (list.isNotEmpty()) {
                mAdapter.loadMoreComplete()
            }
        }
    }
}
