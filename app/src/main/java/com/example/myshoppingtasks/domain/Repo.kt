package com.example.myshoppingtasks.domain

import androidx.lifecycle.LiveData

interface Repo {
    suspend fun addShopItem(item: ShopItem)
    suspend fun editShopItem(item: ShopItem)
    suspend fun removeShopItem(item: ShopItem)
    suspend fun getShopItem(id: Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
}