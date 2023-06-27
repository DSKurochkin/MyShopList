package com.example.myshoppingtasks.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myshoppingtasks.data.RoomRepoImpl
import com.example.myshoppingtasks.domain.ShopItem
import com.example.myshoppingtasks.domain.usekeys.EditShopItem
import com.example.myshoppingtasks.domain.usekeys.GetShopList
import com.example.myshoppingtasks.domain.usekeys.RemoveShopItem

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = RoomRepoImpl(application)

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