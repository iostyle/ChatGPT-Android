package com.iostyle.gpt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.iostyle.gpt.databinding.LayoutChatSystemBinding
import com.iostyle.gpt.databinding.LayoutChatUserBinding
import com.iostyle.gpt.model.ChatItem

class GPTAdapter(items: List<ChatItem>) : BaseMultiItemAdapter<ChatItem>(items) {
    companion object {
        private const val ITEM_USER = 0
        private const val ITEM_SYSTEM = 1
    }

    class UserVH(val viewBinding: LayoutChatUserBinding) : RecyclerView.ViewHolder(viewBinding.root)
    class SystemVH(val viewBinding: LayoutChatSystemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    init {
        addItemType(ITEM_USER, object : OnMultiItemAdapterListener<ChatItem, UserVH> {
            override fun onBind(holder: UserVH, position: Int, item: ChatItem?) {
                holder.viewBinding.contentTv.text = item?.content
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): UserVH {
                val viewBinding =
                    LayoutChatUserBinding.inflate(LayoutInflater.from(context), parent, false)
                return UserVH(viewBinding)
            }
        }).addItemType(ITEM_SYSTEM, object : OnMultiItemAdapterListener<ChatItem, SystemVH> {
            override fun onBind(holder: SystemVH, position: Int, item: ChatItem?) {
                holder.viewBinding.contentTv.text = item?.content
            }

            override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): SystemVH {
                val viewBinding =
                    LayoutChatSystemBinding.inflate(LayoutInflater.from(context), parent, false)
                return SystemVH(viewBinding)
            }
        }).onItemViewType { position, list ->
            if (list[position].role == "User") ITEM_USER
            else ITEM_SYSTEM
        }
    }
}