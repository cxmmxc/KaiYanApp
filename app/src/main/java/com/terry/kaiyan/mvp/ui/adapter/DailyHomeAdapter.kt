package com.terry.kaiyan.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean

/**
 * Author:ChenXinming
 * Date:2019/06/24
 * Description:
 */
class DailyHomeAdapter : BaseQuickAdapter<HomeBean.Issue.HomeItem, BaseViewHolder> {

    constructor() : super( R.layout.item_daily_banner)

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.HomeItem?) {

    }
}
