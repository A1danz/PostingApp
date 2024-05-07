package com.a1danz.feature_settings.presentation.screens.social_media.tg.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a1danz.feature_settings.R
import com.a1danz.feature_settings.databinding.ItemRvVkGroupBinding
import com.a1danz.feature_settings.domain.model.TgChatDomainModel
import com.a1danz.feature_settings.presentation.model.TgChatUiModel
import com.bumptech.glide.Glide

class TgChatsAdapter(
    private val chats: List<TgChatUiModel>,
    private val chosenCallback: ((Boolean, TgChatUiModel) -> Unit)
) : RecyclerView.Adapter<TgChatsAdapter.TgChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TgChatViewHolder {
        return TgChatViewHolder(
            ItemRvVkGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TgChatViewHolder, position: Int) {
        holder.bindItem(chats[position])
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    inner class TgChatViewHolder(private val viewBinding: ItemRvVkGroupBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bindItem(chat: TgChatUiModel) {
            with(viewBinding) {

                tvGroupName.text = chat.chatName
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
}