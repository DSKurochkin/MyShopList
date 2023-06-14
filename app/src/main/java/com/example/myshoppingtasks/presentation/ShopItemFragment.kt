package com.example.myshoppingtasks.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myshoppingtasks.R
import com.example.myshoppingtasks.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private var shopItemId = ShopItem.UNDEFINED_ID
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveBtn: Button

    private lateinit var editingFinishedListener: EditingFinishedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditingFinishedListener) {
            editingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addTextListeners()
        launchRightMode()
        observeViewModel()
    }


    private fun parseParam() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown mode $mode")
        }
        if (mode == EDIT_MODE && !args.containsKey(ID)) {
            throw RuntimeException("Param shop id is absent")
        }
        shopItemId = args.getInt(ID, ShopItem.UNDEFINED_ID)
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        saveBtn = view.findViewById(R.id.save_btn)
    }

    private fun launchRightMode() {
        when (requireArguments().getString(SCREEN_MODE)) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode(shopItemId)
        }
    }

    private fun launchAddMode() {
        saveBtn.setOnClickListener {
            viewModel.addShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun launchEditMode(id: Int) {
        viewModel.getShopItem(id)
        viewModel.item.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        saveBtn.setOnClickListener {
            viewModel.updateShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            editingFinishedListener.outputOk()
//            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel.infoNameError.observe(viewLifecycleOwner) {
            tilName.error = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
        }

        viewModel.infoCountError.observe(viewLifecycleOwner) {
            tilCount.error = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
        }
    }

    private fun addTextListeners() {
        etName.doOnTextChanged { text, start, before, count -> viewModel.resetNameError() }
        etName.doAfterTextChanged { text -> viewModel.resetNameError() }

        etCount.doOnTextChanged() { text, start, before, count ->
            viewModel.resetCountError()
        }
        etCount.doAfterTextChanged { text -> viewModel.resetCountError() }
    }


    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNKNOWN_MODE = "-2"
        private const val ID = "id"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditItem(id: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, EDIT_MODE)
                    putInt(ID, id)
                }
            }
        }

    }
}