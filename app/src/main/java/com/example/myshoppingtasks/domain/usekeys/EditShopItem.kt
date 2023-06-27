package com.example.myshoppingtasks.domain.usekeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

class EditShopItem(private val repo: Repo) {
    fun editShopItem(item: ShopItem) = repo.editShopItem(item)
}