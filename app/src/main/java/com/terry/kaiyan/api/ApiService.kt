package com.terry.kaiyan.api

import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 作者：Terry.CHen
 * 创建日期：2019/06/21
 * 邮箱：herewinner@163.com
 * 描述：TODO
 */
interface ApiService {

    @GET("v2/feed?")
    fun getBannerData(@Query("num") num:Int):Observable<HomeBean>
}
