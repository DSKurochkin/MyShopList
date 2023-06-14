package com.example.myshoppingtasks.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppingtasks.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), EditingFinishedListener {

    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var viewModel: MainViewModel
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)

        }

        val addItemBtn = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        addItemBtn.setOnClickListener {
            if (isPortraitOrientanion()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }

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
                ShopListAdapter.SHORT_CLICK_PARAM -> {
                    if (isPortraitOrientanion()) {
                        val intent = ShopItemActivity.newIntentEditItem(this, item.id)
                        startActivity(intent)
                    } else {
                        launchFragment(ShopItemFragment.newInstanceEditItem(item.id))
                    }
                }

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

    private fun isPortraitOrientanion() = shopItemContainer == null

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun outputOk() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


}
