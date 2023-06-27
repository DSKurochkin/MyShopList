package com.example.myshoppingtasks.domain

import androidx.lifecycle.LiveData

interface Repo {
    fun addShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun removeShopItem(item: ShopItem)
    fun getShopItem(id: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
}