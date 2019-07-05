package com.terry.kaiyan.widget

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

/**
 * Author:ChenXinming
 * Date:2019/07/05
 * Description:
 */
class SpaceItemDecoration : ItemDecoration {

    constructor() : super() {

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
    }
}
