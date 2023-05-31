package com.example.myshoppingtasks.domain.uskeys

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

class GetShopItem (private val repo: Repo){
    fun getShopItem(id:Int):ShopItem = repo.getShopItem(id)
}