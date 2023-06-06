package com.example.myshoppingtasks.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.domain.ShopItem

class ShopListAdapter
    : ListAdapter<ShopItem, ShopListAdapter.ShopListViewHolder>(ShopItemDiffCallback()) {
    var onShopItemClickListener: ((item: ShopItem, clickParam: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val tvItemId = when (viewType) {
            VT_ENABLED -> R.layout.item_shop_enabled
            VT_DISABLED -> R.layout.item_shop_disabled
            else -> {
                throw RuntimeException("Unknown ViewType - $viewType")
            }
        }
        return ShopListViewHolder(
            LayoutInflater
                .from(parent.context).inflate(tvItemId, parent, false)

        )
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        with(holder) {
            val item = getItem(position)
            tvName.text = item.name
            tvCount.text = item.count.toString()
            view.setOnLongClickListener {
                onShopItemClickListener?.invoke(item, LONG_CLICK_PARAM)
                true
            }
            view.setOnClickListener {
                onShopItemClickListener?.invoke(item, SHORT_CLICK_PARAM)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isEnabled) {
            VT_ENABLED
        } else {
            VT_DISABLED
        }
    }

    class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvName = view.findViewById<TextView>(R.id.item_name)
        var tvCount = view.findViewById<TextView>(R.id.item_count)
    }

    companion object {
        val VT_ENABLED = 0
        val VT_DISABLED = 1
        val POOL_SIZE = 15
        val LONG_CLICK_PARAM = 10
        val SHORT_CLICK_PARAM = 1
    }

}
