package com.example.myshoppingtasks.domain

interface Repo {
    fun addShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun removeShopItem(id: Int)
    fun getShopItem(id: Int):ShopItem
    fun getShopList():List<ShopItem>
}