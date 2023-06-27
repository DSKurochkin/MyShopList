package com.example.myshoppingtasks.presentation

import androidx.lifecycle.ViewModel
import com.example.myshoppingtasks.data.InMemRepImpl
import com.example.myshoppingtasks.domain.ShopItem
import com.example.myshoppingtasks.domain.uskeys.EditShopItem
import com.example.myshoppingtasks.domain.uskeys.GetShopList
import com.example.myshoppingtasks.domain.uskeys.RemoveShopItem

class MainViewModel : ViewModel() {
    private val repo = InMemRepImpl

    private val getShopListUseCase = GetShopList(repo)
    private val editShopItemCase = EditShopItem(repo)
    private val deleteShopItemCase = RemoveShopItem(repo)

    val shopList = getShopListUseCase.getShopList()

    fun removeItem(item: ShopItem) {
        deleteShopItemCase.removeShopItem(item)
    }

    fun changeItemEnabled(item: ShopItem) {
        val copy = item.copy(isEnabled = !item.isEnabled)
        editShopItemCase.editShopItem(copy)
    }
}