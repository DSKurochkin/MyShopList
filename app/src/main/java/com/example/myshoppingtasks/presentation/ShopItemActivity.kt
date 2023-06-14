package com.example.myshoppingtasks.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), EditingFinishedListener {


    private var screenMode = UNKNOWN_MODE
    private var itemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            ADD_MODE -> ShopItemFragment.newInstanceAddItem()
            EDIT_MODE -> ShopItemFragment.newInstanceEditItem(itemId)
            else -> throw RuntimeException("Unknown mode - $screenMode")
        }
        supportFragmentManager.beginTransaction().add(R.id.shop_item_container, fragment).commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(SCREEN_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            itemId = intent.getIntExtra(EXTRA_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNKNOWN_MODE = "-1"
        private const val EXTRA_ID = "id"
        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, EDIT_MODE)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
//
    }

    override fun outputOk() {
        finish()
    }

}