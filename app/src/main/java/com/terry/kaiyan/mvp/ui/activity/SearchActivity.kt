package com.terry.kaiyan.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.terry.kaiyan.di.component.DaggerSearchComponent
import com.terry.kaiyan.di.module.SearchModule
import com.terry.kaiyan.mvp.contract.SearchContract
import com.terry.kaiyan.mvp.presenter.SearchPresenter

import com.terry.kaiyan.R


/**
 * Author:ChenXinming
 * Date: 2019/06/25
 * Email:chenxinming@antelop.cloud
 * Description:
 */
class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .searchModule(SearchModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_search //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        setUpEnterAnim()

    }

    private fun setUpEnterAnim() {
        val transient = TransitionInflater.from(this).inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transient
        transient.addListener(object : Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

        })
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
}
