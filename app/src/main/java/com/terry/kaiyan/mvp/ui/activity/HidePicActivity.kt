package com.terry.kaiyan.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.terry.kaiyan.di.component.DaggerHidePicComponent
import com.terry.kaiyan.mvp.contract.HidePicContract
import com.terry.kaiyan.mvp.presenter.HidePicPresenter

import com.terry.kaiyan.R
import com.terry.kaiyan.di.module.HidePicModule
import com.terry.kaiyan.mvp.model.Bean.DouyinBeanBase
import com.terry.kaiyan.mvp.ui.adapter.HidePicAdapter
import com.terry.kaiyan.utils.paramsToSign
import kotlinx.android.synthetic.main.activity_hide_pic.*
import java.lang.StringBuilder


/**
 * Author:ChenXinming
 * Date: 2019/08/29
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
class HidePicActivity : BaseActivity<HidePicPresenter>(), HidePicContract.View,
    BaseQuickAdapter.RequestLoadMoreListener {

    lateinit var mAdapter: HidePicAdapter
    private var mPage: Int = 1

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHidePicComponent //如找不到该类,请编译一下项目
            .builder()
            .hidePicModule(HidePicModule(this))
            .appComponent(appComponent)
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_hide_pic //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        mAdapter = HidePicAdapter()
        picRecyclerView.adapter = mAdapter
        picRecyclerView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        picRecyclerView.itemAnimator = DefaultItemAnimator()
        mAdapter.setOnLoadMoreListener(this, picRecyclerView)
        getData(mPage)
    }

    private fun getData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap["Page"] = page.toString()
        hashMap["Type"] = "2"
        mPresenter?.loadDouyinData(
            "https://api.wxzslm.com/api/Article/LoadData",
            page.toString(),
            "",
            "",
            "2",
            paramsToSign(hashMap)

        )
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
        list?.let {
            val strList = ArrayList<String>()
            for (value in it) {
                val imageList = value.Image.split(",")
                val sb = StringBuilder()
                for (imageStr in imageList) {
                    sb.clear()
                    sb.append(value.DomainName).append(value.Path).append(imageStr)
                    strList.add(sb.toString())
                }
            }
            if (mPage == 1) {
                mAdapter.setNewData(strList)
            } else {
                mAdapter.addData(strList)
                if (strList.isNotEmpty()) {
                    mAdapter.loadMoreComplete()
                }
            }
        }
    }

    override fun onLoadMoreRequested() {
        mPage++
        getData(mPage)
    }
}
