package com.terry.kaiyan.mvp.ui.activity

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.di.component.DaggerSearchComponent
import com.terry.kaiyan.di.module.SearchModule
import com.terry.kaiyan.mvp.contract.SearchContract
import com.terry.kaiyan.mvp.presenter.SearchPresenter
import com.terry.kaiyan.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.toolbar
import kotlinx.android.synthetic.main.fragment_daily.*


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
        this.let {
            var statusBarHeight = getStatusBarHeight(this)
            val lp = toolbar?.layoutParams
            if (lp != null && lp.height > 0) {
                lp.height += statusBarHeight
            }
            toolbar.setPadding(
                0,
                toolbar.paddingTop + statusBarHeight,
                0,
                0
            )
        }
        setUpEnterAnim()
        setExitAnim()

    }

    private fun setExitAnim() {
        val fade = Fade()
        fade.duration = 300
        fade.startDelay = 300
        window.returnTransition = fade
    }

    private fun setUpEnterAnim() {
        val transient = TransitionInflater.from(this).inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transient
        transient.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                transition!!.removeListener(this)
                animateRootView(this@SearchActivity, rootSearchLayout, R.color.color_EEEEEE)
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

    private fun animateRootView(context: Context, rootGroup: View, @ColorRes color: Int) {
        val finalRadius = Math.hypot(rootGroup.width.toDouble(), rootGroup.height.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(
            rootGroup,
            rootGroup.width / 2,
            rootGroup.height / 2,
                centerView.width / 2F,
            finalRadius
        )
        animator.duration = 300
        animator.interpolator = AccelerateDecelerateInterpolator()
        rootGroup.setBackgroundColor(ContextCompat.getColor(context, color))
        animator.start()
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                centerView.visibility = View.GONE
                contentLayout.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
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
