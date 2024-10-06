package com.a1danz.feature_settings.presentation.rv.diff

import androidx.recyclerview.widget.DiffUtil
import com.a1danz.feature_settings.presentation.model.tg.TgChatUiModel
import javax.inject.Inject

class TgChatDiffUtil @Inject constructor() : DiffUtil.ItemCallback<TgChatUiModel>() {
    override fun areItemsTheSame(oldItem: TgChatUiModel, newItem: TgChatUiModel): Boolean {
        return oldItem.chatId == newItem.chatId
    }

    override fun areContentsTheSame(oldItem: TgChatUiModel, newItem: TgChatUiModel): Boolean {
        return oldItem == newItem
    }
}