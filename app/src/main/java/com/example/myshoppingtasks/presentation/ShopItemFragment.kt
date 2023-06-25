package com.example.myshoppingtasks.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myshoppingtasks.databinding.FragmentShopItemBinding
import com.example.myshoppingtasks.domain.ShopItem

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private var shopItemId = ShopItem.UNDEFINED_ID

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding=null")

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
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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


    private fun launchRightMode() {
        when (requireArguments().getString(SCREEN_MODE)) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode(shopItemId)
        }
    }

    private fun launchAddMode() {
        binding.saveBtn.setOnClickListener {
            viewModel.addShopItem(binding.etName.text.toString(), binding.etCount.text.toString())
        }
    }

    private fun launchEditMode(id: Int) {
        viewModel.getShopItem(id)
        binding.saveBtn.setOnClickListener {
            viewModel.updateShopItem(
                binding.etName.text.toString(),
                binding.etCount.text.toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            editingFinishedListener.outputOk()
        }
    }

    private fun addTextChangeListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetNameError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetCountError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
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