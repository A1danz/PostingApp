package com.a1danz.feature_settings.presentation.screens.social_media.tg.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.a1danz.feature_settings.databinding.ItemRvTgChatBinding
import com.a1danz.feature_settings.databinding.ItemRvTgChatPreviewBinding
import com.a1danz.feature_settings.presentation.model.TgChatUiModel
import com.bumptech.glide.Glide

class TgChatsAdapter(
    private val chosenCallback: ((Boolean, TgChatUiModel) -> Unit)
) : RecyclerView.Adapter<ViewHolder>() {
    private var chats: List<TgChatUiModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == LOADED_VIEW_TYPE) {
            TgChatViewHolder(
                ItemRvTgChatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            LoadingViewHolder(
                ItemRvTgChatPreviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING_VIEW_TYPE -> {}
            LOADED_VIEW_TYPE -> {
                chats?.get(position)?.let {
                    (holder as? TgChatViewHolder)?.bindItem(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return chats?.size ?: 10
    }

    override fun getItemViewType(position: Int): Int {
        return if (chats == null) LOADING_VIEW_TYPE else LOADED_VIEW_TYPE
    }

    fun setItems(items: List<TgChatUiModel>) {
        this.chats = items
        notifyDataSetChanged()
    }

    inner class TgChatViewHolder(private val viewBinding: ItemRvTgChatBinding) :
        ViewHolder(viewBinding.root) {

        fun bindItem(chat: TgChatUiModel) {
            with(viewBinding) {

                tvChatName.text = chat.chatName
                Glide.with(viewBinding.root)
                    .load(chat.chatPhoto)
                    .into(ivGroupImg)

                switcher.isChecked = chat.isSelected
                switcher.setOnCheckedChangeListener { btnView, isChecked ->
                    Log.d("CHECKED CHANGE ADAPTER", "STATE: $isChecked")
                    chosenCallback.invoke(isChecked, chat)
                }
            }
        }
    }

    inner class LoadingViewHolder(private val viewBinding: ItemRvTgChatPreviewBinding)
        : ViewHolder(viewBinding.root)

    companion object {
        const val LOADING_VIEW_TYPE = 0
        const val LOADED_VIEW_TYPE = 1
    }
}