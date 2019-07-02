package com.terry.kaiyan.mvp.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.*
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.di.component.DaggerSearchComponent
import com.terry.kaiyan.di.module.SearchModule
import com.terry.kaiyan.mvp.contract.SearchContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.mvp.presenter.SearchPresenter
import com.terry.kaiyan.mvp.ui.adapter.HotDataAdapter
import com.terry.kaiyan.mvp.ui.adapter.HotWordsAdapter
import com.terry.kaiyan.utils.closeKeyBord
import com.terry.kaiyan.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_search.*


/**
 * Author:ChenXinming
 * Date: 2019/06/25
 * Email:chenxinming@antelop.cloud
 * Description:
 */
class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var mHotsAdapter:HotWordsAdapter ?= null
    private var mHotsDataAdapter: HotDataAdapter?= null
    private var mKeyWords:String ?= null

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
        cancelTv.setOnClickListener(this)
        mHotsAdapter = HotWordsAdapter(R.layout.item_hot_words)
        var flexLayoutManager = FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP)
        flexLayoutManager.justifyContent =JustifyContent.FLEX_START
        flexLayoutManager.alignItems = AlignItems.CENTER
        tagRecyclerView.layoutManager = flexLayoutManager
        tagRecyclerView.adapter = mHotsAdapter
        mPresenter?.getHotWords()
        mHotsAdapter?.setOnItemChildClickListener { adapter, view, position ->
            //进行搜索
            mKeyWords = adapter.getItem(position) as String
            mPresenter?.getHotDatas(mKeyWords!!)
        }
        mHotsDataAdapter = HotDataAdapter(this, R.layout.item_hot_data)
        mHotsDataAdapter?.setOnLoadMoreListener(this, contentRecyclerView)
        contentRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        contentRecyclerView.itemAnimator = DefaultItemAnimator()
        contentRecyclerView.adapter = mHotsDataAdapter
        searchEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                mKeyWords = searchEt.text.toString()
                if (mKeyWords?.trim()?.isNotEmpty()!!) {
                    mPresenter?.getHotDatas(mKeyWords!!)
                    closeKeyBord(searchEt, this@SearchActivity)
                    return@setOnEditorActionListener true
                }
                ArmsUtils.snackbarText(getString(R.string.hint_not_empty_keywords))
                return@setOnEditorActionListener false
            }
            false
        }
    }

    private fun setExitAnim() {
        val fade = Fade()
        fade.duration = 300
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
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cancelTv -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cX = (rootSearchLayout.left + rootSearchLayout.right) / 2
            val cY = (rootSearchLayout.top + rootSearchLayout.height) / 2
            val startRadius = Math.hypot(rootSearchLayout.width.toDouble(), rootSearchLayout.height.toDouble()).toFloat()
            var animView = ViewAnimationUtils.createCircularReveal(rootSearchLayout, cX, cY, startRadius / 2F, centerView.width.toFloat() / 2F)
            animView.duration = 300
            animView.interpolator = AccelerateDecelerateInterpolator()
            animView.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
//                    rootSearchLayout.visibility = View.INVISIBLE
                    defaultBackPress()
                }

                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    centerView.visibility = View.VISIBLE
                    contentLayout.visibility = View.INVISIBLE
                    rootSearchLayout.setBackgroundColor(ContextCompat.getColor(this@SearchActivity, R.color.color_EEEEEE))
                }
            })
            animView.start()

        } else {
            defaultBackPress()
        }
    }

    private fun defaultBackPress() {
        //隐藏输入法
        super.onBackPressed()
    }

    override fun getHotWordsSuccess(list: ArrayList<String>) {
        mHotsAdapter?.setNewData(list)
    }

    override fun getHotWordsFail() {

    }

    override fun getHotDatasSuccess(homeBean: HomeBean.Issue) {
        hotDataTotalTv.text = "- [$mKeyWords]搜索结果共${homeBean.total}个 -"
        tagLayout.visibility = View.GONE
        contentRealLayout.visibility = View.VISIBLE
        mHotsDataAdapter?.setNewData(homeBean.itemList)
        if (homeBean.itemList.isNotEmpty()) {
            contentRecyclerView.smoothScrollToPosition(0)
        }
        if (homeBean.nextPageUrl.isNullOrEmpty()) {
            mHotsDataAdapter?.loadMoreEnd(true)
        } else {
            mHotsDataAdapter?.loadMoreComplete()
        }
    }

    override fun getMoreHotDatasSuccess(homeBean: HomeBean.Issue) {
        mHotsDataAdapter?.addData(homeBean.itemList)
        if (homeBean.nextPageUrl.isNullOrEmpty()) {
            mHotsDataAdapter?.loadMoreEnd(false)
        } else {
            mHotsDataAdapter?.loadMoreComplete()
        }
    }

    override fun getMoreHotDatasFail() {
        mHotsDataAdapter?.loadMoreFail()
    }

    override fun getHotDatasFail() {
    }
    override fun onLoadMoreRequested() {
        mPresenter?.getMoreHotDatas()
    }
}
