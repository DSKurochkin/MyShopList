package com.example.myshoppingtasks.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshoppingtasks.data.InMemRepImpl
import com.example.myshoppingtasks.domain.ShopItem
import com.example.myshoppingtasks.domain.uskeys.AddShopItem
import com.example.myshoppingtasks.domain.uskeys.EditShopItem
import com.example.myshoppingtasks.domain.uskeys.GetShopItem

class ShopItemViewModel : ViewModel() {
    private val repo = InMemRepImpl
    private val getShopItemUseCase = GetShopItem(repo)
    private val updateShopItemUseCase = EditShopItem(repo)
    private val addShopItemUseCase = AddShopItem(repo)


    private val _infoNameError = MutableLiveData<Boolean>()
    val infoNameError: LiveData<Boolean>
        get() = _infoNameError


    private val _infoCountError = MutableLiveData<Boolean>()
    val infoCountError: LiveData<Boolean>
        get() = _infoCountError

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _item = MutableLiveData<ShopItem>()
    val item: LiveData<ShopItem>
        get() = _item


    fun getShopItem(id: Int) {
        _item.value = getShopItemUseCase.getShopItem(id)
    }

    fun updateShopItem(name: String?, count: String?) {
        val pName = parseName(name)
        val pCount = parseCount(count)
        if (isValidateInput(pName, pCount)) {
            _item.value?.let {
                val item = it.copy(pName, pCount)
                updateShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    fun addShopItem(name: String?, count: String?) {
        val pName = parseName(name)
        val pCount = parseCount(count)
        if (isValidateInput(pName, pCount)) {
            addShopItemUseCase.addShopItem(ShopItem(pName, pCount, true))
            finishWork()
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: 0
        } catch (r: NumberFormatException) {
            0
        }
    }

    private fun isValidateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _infoNameError.value = true
            result = false
        }
        if (count <= 0) {
            _infoCountError.value = true
            result = false
        }
        return result
    }

    fun resetNameError() {
        _infoNameError.value = false
    }

    fun resetCountError() {
        _infoCountError.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}