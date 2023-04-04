package com.iostyle.gpt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.iostyle.gpt.databinding.LayoutChatSystemBinding
import com.iostyle.gpt.databinding.LayoutChatUserBinding

@OptIn(BetaOpenAI::class)
class GPTAdapter(items: List<ChatMessage>) : BaseMultiItemAdapter<ChatMessage>(items) {
    companion object {
        private const val ITEM_USER = 0
        private const val ITEM_SYSTEM = 1
    }

    class UserVH(val viewBinding: LayoutChatUserBinding) : RecyclerView.ViewHolder(viewBinding.root)
    class SystemVH(val viewBinding: LayoutChatSystemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    init {
        addItemType(ITEM_USER, object : OnMultiItemAdapterListener<ChatMessage, UserVH> {
            override fun onBind(holder: UserVH, position: Int, item: ChatMessage?) {
                holder.viewBinding.contentTv.text = item?.content
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): UserVH {
                val viewBinding =
                    LayoutChatUserBinding.inflate(LayoutInflater.from(context), parent, false)
                return UserVH(viewBinding)
            }
        }).addItemType(ITEM_SYSTEM, object : OnMultiItemAdapterListener<ChatMessage, SystemVH> {
            override fun onBind(holder: SystemVH, position: Int, item: ChatMessage?) {
                holder.viewBinding.contentTv.text = item?.content
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): SystemVH {
                val viewBinding =
                    LayoutChatSystemBinding.inflate(LayoutInflater.from(context), parent, false)
                return SystemVH(viewBinding)
            }
        }).onItemViewType { position, list ->
            if (list[position].role == ChatRole.User) ITEM_USER
            else ITEM_SYSTEM
        }
    }
}