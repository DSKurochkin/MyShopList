package com.example.myshoppingtasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshoppingtasks.domain.ShopItem
import com.example.myshoppingtasks.domain.usekeys.EditShopItem
import com.example.myshoppingtasks.domain.usekeys.GetShopList
import com.example.myshoppingtasks.domain.usekeys.RemoveShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopList,
    private val editShopItemCase: EditShopItem,
    private val deleteShopItemCase: RemoveShopItem
) : ViewModel() {


    val shopList = getShopListUseCase.getShopList()

    fun removeItem(item: ShopItem) {
        viewModelScope.launch {
            deleteShopItemCase.removeShopItem(item)
        }
    }

    fun changeItemEnabled(item: ShopItem) {
        viewModelScope.launch {
            val copy = item.copy(isEnabled = !item.isEnabled)
            editShopItemCase.editShopItem(copy)
        }
    }
}