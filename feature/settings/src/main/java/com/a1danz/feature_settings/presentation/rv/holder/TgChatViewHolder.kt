package com.a1danz.feature_settings.presentation.rv.holder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_settings.databinding.ItemRvTgChatBinding
import com.a1danz.feature_settings.presentation.model.tg.TgChatUiModel
import com.bumptech.glide.Glide

class TgChatViewHolder(
    private val viewBinding: ItemRvTgChatBinding,
    private val chosenCallback: (Boolean, TgChatUiModel) -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var tgChatUiModel: TgChatUiModel? = null

    init {
        viewBinding.switcher.setOnCheckedChangeListener { btnView, isChecked ->
            tgChatUiModel?.let {
                chosenCallback.invoke(isChecked, it)
            }
        }
    }

    fun bindItem(chat: TgChatUiModel) {
        with(viewBinding) {
            Glide.with(viewBinding.root)
                .load(chat.chatPhoto)
                .into(ivGroupImg)

            tvChatName.text = chat.chatName

            switcher.isChecked = chat.isSelected
        }
    }
}