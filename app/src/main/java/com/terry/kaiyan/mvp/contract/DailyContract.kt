package com.terry.kaiyan.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.Observable


/**
 * Author:ChenXinming
 * Date: 2019/06/21
 * Email:chenxinming@antelop.cloud
 * Description:
 */
interface DailyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun getHomeBannerSuccess(homeBean: HomeBean?)
        fun getHomeBannerFail()
        fun getHomeListSuccess(homeBean: HomeBean?)
        fun getHomeListFail()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun getHomeBanner(num:Int): Observable<HomeBean>
        fun getMoreData(url:String): Observable<HomeBean>
    }

}
