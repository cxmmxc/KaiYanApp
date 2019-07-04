package com.terry.kaiyan.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.di.component.DaggerHomeComponent
import com.terry.kaiyan.di.module.HomeModule
import com.terry.kaiyan.mvp.contract.HomeContract
import com.terry.kaiyan.mvp.presenter.HomePresenter
import com.terry.kaiyan.mvp.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.activity_home.*


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
class HomeActivity : BaseActivity<HomePresenter>(), HomeContract.View {

    private var homeAdapter:HomeAdapter ?= null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_home //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        homeAdapter = HomeAdapter(this)
        main_pager2.adapter = homeAdapter
        main_pager2.isUserInputEnabled = false
        main_pager2.offscreenPageLimit = 3
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                main_pager2.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_discover -> {
                main_pager2.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_hot -> {
                main_pager2.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_mine -> {
                main_pager2.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
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
