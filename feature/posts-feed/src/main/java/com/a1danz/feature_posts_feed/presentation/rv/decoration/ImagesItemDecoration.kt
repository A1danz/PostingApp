package com.a1danz.feature_posts_feed.presentation.rv.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ImagesItemDecoration(
    private val spaceBetweenHorizontal: Int,
): ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position != 0) {
            outRect.left = spaceBetweenHorizontal
        }
    }
}