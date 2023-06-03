package com.example.myshoppingtasks.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.domain.ShopItem

class MainActivity : ComponentActivity() {

    private lateinit var llShopList: LinearLayout
    private lateinit var viewModel: MaiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MaiViewModel::class.java)
        llShopList = findViewById(R.id.ll_shop_list)

        viewModel.shopList.observe(this) {
            showList(it)
        }

    }

    fun showList(list: List<ShopItem>) {
        llShopList.removeAllViews()
        for (item in list) {
            val loutId = if (item.isEnabled) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }

            val llView = LayoutInflater.from(this).inflate(loutId, llShopList, false)

            val tvName = llView.findViewById<TextView>(R.id.item_name)
            val tvCount = llView.findViewById<TextView>(R.id.item_count)

            tvName.text = item.name
            tvCount.text = item.count.toString()
            llShopList.addView(llView)

            llView.setOnLongClickListener {
                viewModel.changeItemEnabled(item)
                true
            }
        }
    }
}

