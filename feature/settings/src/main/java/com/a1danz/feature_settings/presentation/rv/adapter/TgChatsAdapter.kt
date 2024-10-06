package com.a1danz.feature_settings.presentation.rv.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.a1danz.feature_settings.databinding.ItemRvTgChatBinding
import com.a1danz.feature_settings.databinding.ItemRvTgChatPreviewBinding
import com.a1danz.feature_settings.presentation.model.tg.TgChatUiModel
import com.a1danz.feature_settings.presentation.rv.Config
import com.a1danz.feature_settings.presentation.rv.diff.TgChatDiffUtil
import com.a1danz.feature_settings.presentation.rv.holder.TgChatLoadingViewHolder
import com.a1danz.feature_settings.presentation.rv.holder.TgChatViewHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

class TgChatsAdapter(
    private val chosenCallback: ((Boolean, TgChatUiModel) -> Unit),
) : ListAdapter<TgChatUiModel, ViewHolder>(TgChatDiffUtil()) {
    private var isLoading = true
        set(value) {
            if (value != isLoading) {
                field = value
                notifyItemRangeChanged(0, Config.LOADING_ITEM_COUNT)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == LOADED_VIEW_TYPE) {
            TgChatViewHolder(
                viewBinding = ItemRvTgChatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                chosenCallback = chosenCallback,
            )
        } else {
            TgChatLoadingViewHolder(
                ItemRvTgChatPreviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun submitListWithDisableLoading(items: List<TgChatUiModel>) {
        isLoading = false
        submitList(items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING_VIEW_TYPE -> {}
            LOADED_VIEW_TYPE -> {
                (holder as? TgChatViewHolder)?.bindItem(getItem(position))
                    ?: Log.e("Err", "Failed cast to TgChatViewHolder - $holder")
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) Config.LOADING_ITEM_COUNT else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) LOADING_VIEW_TYPE else LOADED_VIEW_TYPE
    }

    companion object {
        const val LOADING_VIEW_TYPE = 0
        const val LOADED_VIEW_TYPE = 1
    }
}