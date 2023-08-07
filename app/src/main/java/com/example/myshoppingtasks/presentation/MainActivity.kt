package com.example.myshoppingtasks.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.ShopItemApp
import com.example.myshoppingtasks.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), EditingFinishedListener {

    private lateinit var shopListAdapter: ShopListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ShopItemApp).component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)

        }

        binding.buttonAddShopItem.setOnClickListener {
            if (isPortraitOrientation()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvShopList) {
            shopListAdapter = ShopListAdapter()
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
                    if (isPortraitOrientation()) {
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

    private fun isPortraitOrientation() = binding.shopItemContainer == null

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
