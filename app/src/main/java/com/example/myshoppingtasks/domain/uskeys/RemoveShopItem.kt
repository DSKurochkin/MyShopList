package com.example.myshoppingtasks.domain.uskeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

class RemoveShopItem(private val repo: Repo) {
    fun removeShopItem(item: ShopItem) {
        repo.removeShopItem(item)
    }
}