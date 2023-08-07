package com.example.myshoppingtasks.domain.usekeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem
import javax.inject.Inject

class RemoveShopItem @Inject constructor (private val repo: Repo) {
    suspend fun removeShopItem(item: ShopItem) {
        repo.removeShopItem(item)
    }
}