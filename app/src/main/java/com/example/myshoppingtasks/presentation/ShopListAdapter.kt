package com.example.myshoppingtasks.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.databinding.ItemShopDisabledBinding
import com.example.myshoppingtasks.databinding.ItemShopEnabledBinding
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
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            tvItemId,
            parent,
            false
        )
        return ShopListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        with(holder) {
            val item = getItem(position)
            val binding = holder.binding
            with(binding.root) {
                setOnClickListener {
                    onShopItemClickListener?.invoke(item, SHORT_CLICK_PARAM)
                }
                setOnLongClickListener {
                    onShopItemClickListener?.invoke(item, LONG_CLICK_PARAM)
                    true
                }
            }
            when (binding) {
                is ItemShopEnabledBinding -> {
                    binding.item = item
                }

                is ItemShopDisabledBinding -> {
                    binding.item = item
                }
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

    class ShopListViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
    companion object {
        val VT_ENABLED = 0
        val VT_DISABLED = 1
        val POOL_SIZE = 15
        val LONG_CLICK_PARAM = 10
        val SHORT_CLICK_PARAM = 1
    }

}
