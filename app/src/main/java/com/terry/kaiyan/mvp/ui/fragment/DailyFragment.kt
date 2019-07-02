package com.terry.kaiyan.mvp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.transition.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.app.OnPrepareListener
import com.terry.kaiyan.di.component.DaggerDailyComponent
import com.terry.kaiyan.di.module.DailyModule
import com.terry.kaiyan.mvp.contract.DailyContract
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import com.terry.kaiyan.mvp.presenter.DailyPresenter
import com.terry.kaiyan.mvp.ui.activity.SearchActivity
import com.terry.kaiyan.mvp.ui.adapter.DailyBannerAdapter
import com.terry.kaiyan.mvp.ui.adapter.DailyHomeAdapter
import com.terry.kaiyan.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.fragment_daily.view.*
import kotlinx.android.synthetic.main.item_daily_banner.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
class DailyFragment : BaseFragment<DailyPresenter>(), DailyContract.View, SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener {

    private var mAdapter: DailyHomeAdapter? = null
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var bannerAdapter: DailyBannerAdapter
    private lateinit var bannerView: View
    private var isFirstRefresh: Boolean = true

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    companion object {
        const val AUTO_SCROLL_DELAY = 3000L
        const val AUTO_SCROLL_WHAT = 1
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
        dailySwipeLayout.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_red_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_green_light
        )
        mAdapter = DailyHomeAdapter(context)
        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        dailyRecyclerView.layoutManager = layoutManager
        dailyRecyclerView.itemAnimator = DefaultItemAnimator()
        dailyRecyclerView.adapter = mAdapter
        inflateHeader()
        dailyRecyclerView.postDelayed({ onRefresh() }, 150)
        mAdapter?.setOnLoadMoreListener(this, dailyRecyclerView)
        activity.let {
            var statusBarHeight = getStatusBarHeight(context)
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
        dailyRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
                if (firstVisiblePosition == 0) {
                    toolbar.setBackgroundColor(resources.getColor(R.color.transparent))
                    dailyTitleSearchIv.setImageResource(R.drawable.ic_action_search_white)
                    dailyTitleTv.text = ""
                    val hasMessage = delayHandler.hasMessages(AUTO_SCROLL_WHAT)
                    if (!hasMessage) {
                        delayHandler.sendEmptyMessageDelayed(AUTO_SCROLL_WHAT, AUTO_SCROLL_DELAY)
                    }
                } else {
                    val item = mAdapter?.getItem(firstVisiblePosition - 1)
                    toolbar.setBackgroundColor(resources.getColor(R.color.color_title_bg))
                    dailyTitleSearchIv.setImageResource(R.drawable.ic_action_search_black)
                    if (item?.type == "textHeader") {
                        dailyTitleTv.text = item.data.text
                    } else {
                        dailyTitleTv.text = simpleDateFormat.format(item?.data?.date)
                    }
                    val hasMessage = delayHandler.hasMessages(AUTO_SCROLL_WHAT)
                    if (hasMessage) {
                        delayHandler.removeMessages(AUTO_SCROLL_WHAT)
                    }
                }
            }
        })
        dailyTitleSearchIv.setOnClickListener {
            activity?.let {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    it,
                    dailyTitleSearchIv,
                    dailyTitleSearchIv.transitionName
                )
                startActivity(Intent(it, SearchActivity::class.java), options.toBundle())
            }
        }
        val sl = Slide()
        sl.duration = 500
        sl.slideEdge = Gravity.LEFT
        sl.excludeTarget(R.id.toolbar, true)
        activity?.window?.reenterTransition = sl
        activity?.window?.exitTransition = sl
    }

    private fun inflateHeader() {
        bannerView = layoutInflater.inflate(R.layout.item_daily_banner, null)
        bannerAdapter = DailyBannerAdapter(activity!!)
        bannerView.dailyViewPager.adapter = bannerAdapter
        bannerView.dailyViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mAdapter?.addHeaderView(bannerView)
        bannerView.dailyViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val item = bannerAdapter.getItem(position)
                bannerView.bannerTitle.text = item?.data?.title
            }
        })
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
        dailySwipeLayout?.isRefreshing = true
        mPresenter?.getDailyFirstData()
    }

    override fun getHomeBannerFail() {
        dailySwipeLayout.isRefreshing = false
    }

    override fun getHomeBannerSuccess(homeBean: ArrayList<HomeBean.Issue.HomeItem>?) {
        dailySwipeLayout.isRefreshing = false
        bannerAdapter.setNewData(homeBean)
        if (isFirstRefresh) {
            val newActivity = activity as OnPrepareListener
            newActivity.prepare()
            isFirstRefresh = false
        }
    }

    override fun getHomeListSuccess(refresh: Boolean, homeBean: ArrayList<HomeBean.Issue.HomeItem>?) {
        if (refresh) {
            dailySwipeLayout.isRefreshing = false
            mAdapter?.setNewData(homeBean)
        } else {
            mAdapter?.addData(homeBean!!)
            if (homeBean == null || homeBean.size == 0) {
                mAdapter?.loadMoreEnd(false)
            } else {
                mAdapter?.loadMoreComplete()
            }
        }
    }

    override fun getHomeListFail() {

    }

    override fun onLoadMoreRequested() {
        mPresenter?.getDailyMoreData()
    }


    private val delayHandler: Handler = object : Handler(Looper.myLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                AUTO_SCROLL_WHAT -> {
                    var currentPosition = bannerView.dailyViewPager.currentItem
                    when {
                        currentPosition + 1 == bannerAdapter.data.size -> {
                            currentPosition = 0
                            bannerView.dailyViewPager.setCurrentItem(currentPosition, false)
                        }
                        currentPosition + 1 < bannerAdapter.data.size -> {
                            currentPosition += 1
                            bannerView.dailyViewPager.currentItem = currentPosition
                        }
                    }
                    sendEmptyMessageDelayed(AUTO_SCROLL_WHAT, AUTO_SCROLL_DELAY)
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        delayHandler.removeMessages(AUTO_SCROLL_WHAT)
    }

    override fun onStop() {
        super.onStop()
        delayHandler.removeMessages(AUTO_SCROLL_WHAT)
    }

    override fun onResume() {
        super.onResume()
        if (bannerAdapter.data.isNotEmpty()) {
            delayHandler.removeMessages(AUTO_SCROLL_WHAT)
            delayHandler.sendEmptyMessageDelayed(AUTO_SCROLL_WHAT, AUTO_SCROLL_DELAY)
        }
    }

}
