package com.example.myshoppingtasks.domain.uskeys

import androidx.lifecycle.MutableLiveData
import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

class GetShopList(private val repo: Repo) {
    fun getShopList(): MutableLiveData<List<ShopItem>> = repo.getShopList()

}