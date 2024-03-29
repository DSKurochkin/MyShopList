package com.example.myshoppingtasks.domain.usekeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem
import javax.inject.Inject

class EditShopItem @Inject constructor(private val repo: Repo) {
    suspend fun editShopItem(item: ShopItem) = repo.editShopItem(item)
}