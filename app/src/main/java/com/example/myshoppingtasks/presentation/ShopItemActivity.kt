package com.example.myshoppingtasks.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveBtn: Button

    private var screenMode = UNKNOWN_MODE
    private var itemId = ShopItem.UNDEFINED_ID

    private lateinit var viewModel: ShopItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        parseIntent()
        setMode()
        addTextListeners()
        subscribeOnViewModelEvents()

    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
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

    private fun setMode() {
        when (screenMode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        val item = viewModel.getShopItem(itemId)
        viewModel.item.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        saveBtn.setOnClickListener {
            viewModel.updateShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun launchAddMode() {
        saveBtn.setOnClickListener {
            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        saveBtn = findViewById(R.id.save_btn)
    }

    private fun subscribeOnViewModelEvents() {

        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }

        viewModel.infoNameError.observe(this) {
            tilName.error = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
        }

        viewModel.infoCountError.observe(this) {
            tilCount.error = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
        }
    }


    private fun addTextListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetNameError()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetCountError()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNKNOWN_MODE = "-1"
        private const val EXTRA_ID = "id"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }

    }

}