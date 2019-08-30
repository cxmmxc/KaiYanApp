package com.terry.kaiyan.api

import com.terry.kaiyan.mvp.model.Bean.DouyinBeanBase
import com.terry.kaiyan.mvp.model.Bean.HomeBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * 作者：Terry.CHen
 * 创建日期：2019/06/21
 * 邮箱：herewinner@163.com
 * 描述：TODO
 */
interface ApiService {

    @GET("v2/feed?")
    fun getBannerData(@Query("num") num: Int): Observable<HomeBean>

    @GET
    fun getMoreData(@Url url: String): Observable<HomeBean>

    @GET("v3/queries/hot")
    fun getHotWords(): Observable<ArrayList<String>>

    @GET("v1/search")
    fun getSearchHots(@Query("num") num: Int, @Query("start") start: Int, @Query("query") query: String): Observable<HomeBean.Issue>

    @GET
    fun getSearchMoreHots(@Url url: String): Observable<HomeBean.Issue>

    @GET("v4/video/related")
    fun getRelated(@Query("id") id: Long?): Observable<HomeBean.Issue>

    @POST
    @FormUrlEncoded
    fun getDouyinData(
        @Url url: String, @Field("Page") page: String?, @Field("VibratoId") vibratoId: String?, @Field(
            "SortType"
        ) sortType: String?, @Field("Type") type: String?, @Field("Sign") sign: String?
    ): Observable<DouyinBeanBase>
}
