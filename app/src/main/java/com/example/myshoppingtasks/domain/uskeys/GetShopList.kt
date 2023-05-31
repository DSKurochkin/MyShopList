package com.example.myshoppingtasks.domain.uskeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

class GetShopList(private val repo: Repo) {
    fun getShopList(): List<ShopItem> = repo.getShopList()

}