package com.example.myshoppingtasks.domain.usekeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

class AddShopItem(private val repo: Repo) {

    fun addShopItem(item: ShopItem) = repo.addShopItem(item)
}