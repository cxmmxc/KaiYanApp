package com.terry.kaiyan.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.terry.kaiyan.di.component.DaggerDiscoverLikeComponent
import com.terry.kaiyan.di.module.DiscoverLikeModule
import com.terry.kaiyan.mvp.contract.DiscoverLikeContract
import com.terry.kaiyan.mvp.presenter.DiscoverLikePresenter

import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.ui.activity.HideActivity
import kotlinx.android.synthetic.main.fragment_discover_like.*


/**
 * Author:ChenXinming
 * Date: 2019/07/09
 * Email:chenxinming@antelop.cloud
 * Description:
 */


class DiscoverLikeFragment : BaseFragment<DiscoverLikePresenter>(), DiscoverLikeContract.View {


    val COUNTS = 5
    val DURATION = 3*1000L
    val mHits = LongArray(COUNTS)

    companion object {
        fun newInstance(): DiscoverLikeFragment {
            return DiscoverLikeFragment()
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDiscoverLikeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .discoverLikeModule(DiscoverLikeModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_discover_like, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        hideTv.setOnClickListener {
            //点击5次进入隐藏页面
            System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
            mHits[mHits.size-1] = SystemClock.uptimeMillis()
            if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
                startActivity(Intent(activity, HideActivity::class.java))
            }
        }
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
