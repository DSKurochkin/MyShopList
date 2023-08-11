package com.example.myshoppingtasks.domain.usekeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem
import javax.inject.Inject

class GetShopItem @Inject constructor(private val repo: Repo) {
    suspend fun getShopItem(id: Int): ShopItem = repo.getShopItem(id)
}