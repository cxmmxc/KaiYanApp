package com.terry.kaiyan.mvp.ui.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.terry.kaiyan.R
import com.terry.kaiyan.mvp.model.Bean.HomeBean

/**
 * Author:ChenXinming
 * Date:2019/06/26
 * Description:
 */
class DailyHomeAdapter2<T> : RecyclerView.Adapter<HomeViewHolder> {


    var context: Context ?= null
    lateinit var inflater:LayoutInflater
    private val dataList: ArrayList<T> = ArrayList()
    var imageLoader:ImageLoader ?= null

    constructor(context: Context?) {
        this.context = context
        inflater = LayoutInflater.from(context)
        imageLoader = ArmsUtils.obtainAppComponentFromContext(context).imageLoader()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return when (viewType) {
            1 -> HomeViewHolder(inflateLayout(R.layout.item_daily_header, parent))
            0 -> HomeViewHolder(inflateLayout(R.layout.item_daily_list, parent))
            else -> HomeViewHolder(inflateLayout(R.layout.item_daily_list, parent))
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = dataList.get(position) as HomeBean.Issue.HomeItem
        when (item.type) {
            "textHeader" -> holder.getView<TextView>(R.id.daily_header_tv).text = item.data.text
            else -> {
                val coverIv = holder.getView<ImageView>(R.id.cover_iv)
                imageLoader?.loadImage(
                    context, ImageConfigImpl.builder()
                        .url(item.data.cover.feed)
                        .imageView(coverIv)
                        .isCenterCrop(true)
                        .build()
                )
            }
        }

    }

    fun getItem(position: Int): T {
        return dataList.get(position)
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataList?.get(position) as HomeBean.Issue.HomeItem
        val type =item?.type
        return when(type){
            "textHeader" -> 1
            else -> 0
        }
    }

    private fun inflateLayout(layoutId: Int, parent: ViewGroup) : View{
        val view = inflater.inflate(layoutId, parent, false)
        return view ?: View(parent.context)
    }

    public fun setNewData(list: ArrayList<T>?) {
        dataList.clear()
        dataList.addAll(list!!)
        notifyDataSetChanged()
    }

}


class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var sparseArray: SparseArray<View> ?= null

    init {
        sparseArray = SparseArray<View>()
    }

    fun <T : View> getView(id: Int): T {
        var view: View? = sparseArray?.get(id)
        if (view == null) {
            view = itemView.findViewById(id)
            sparseArray?.put(id, view)
        }
        return view as T
    }
}

