package com.example.myshoppingtasks.domain.usekeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem
import javax.inject.Inject

class AddShopItem @Inject constructor(private val repo: Repo) {

    suspend fun addShopItem(item: ShopItem) = repo.addShopItem(item)
}