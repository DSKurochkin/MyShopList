package com.example.myshoppingtasks.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppingtasks.R

class MainActivity : ComponentActivity() {

    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var viewModel: MaiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MaiViewModel::class.java)
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)

        }

    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VT_ENABLED,
                ShopListAdapter.POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VT_DISABLED,
                ShopListAdapter.POOL_SIZE
            )
            setupSwipeHelper().attachToRecyclerView(this)
        }
        setupClickListeners()
    }

    private fun setupClickListeners() {
        shopListAdapter.onShopItemClickListener = { item, clickParam ->
            when (clickParam) {
                ShopListAdapter.SHORT_CLICK_PARAM -> Log.d("CLICK", "I'Mto CLICK")
                ShopListAdapter.LONG_CLICK_PARAM -> viewModel.changeItemEnabled(item)
            }
        }
    }

    private fun setupSwipeHelper(): ItemTouchHelper {
        val cb = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(
                    0,
                    ItemTouchHelper.END or ItemTouchHelper.START
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.removeItem(shopListAdapter.currentList[viewHolder.bindingAdapterPosition])
            }
        }
        return ItemTouchHelper(cb)
    }

}
