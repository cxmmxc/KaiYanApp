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
import com.terry.kaiyan.R
import com.terry.kaiyan.di.component.DaggerMineComponent
import com.terry.kaiyan.di.module.MineModule
import com.terry.kaiyan.mvp.contract.MineContract
import com.terry.kaiyan.mvp.presenter.MinePresenter


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */


class MineFragment : BaseFragment<MinePresenter>(), MineContract.View {
    companion object {
        fun newInstance(): MineFragment {
            val fragment = MineFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .mineModule(MineModule(this))
            .build()
            .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {

        LogUtils.debugInfo("cxm", "MineerFragment")
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
