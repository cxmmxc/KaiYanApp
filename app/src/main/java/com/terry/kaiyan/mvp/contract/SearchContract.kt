package com.terry.kaiyan.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.Observable


/**
 * Author:ChenXinming
 * Date: 2019/06/25
 * Email:chenxinming@antelop.cloud
 * Description:
 */
interface SearchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun getHotWordsSuccess(list:ArrayList<String>)
        fun getHotWordsFail()
        fun getHotDatasSuccess(homeBean: HomeBean.Issue)
        fun getMoreHotDatasSuccess(homeBean: HomeBean.Issue)
        fun getHotDatasFail()
        fun getMoreHotDatasFail()
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getHotWords():Observable<ArrayList<String>>
        fun getHotDataWords(keywords:String):Observable<HomeBean.Issue>
        fun getMoreHotDataWords(url:String):Observable<HomeBean.Issue>
    }

}
