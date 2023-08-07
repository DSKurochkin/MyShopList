package com.example.myshoppingtasks.domain.usekeys

import androidx.lifecycle.LiveData
import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem
import javax.inject.Inject

class GetShopList @Inject constructor(private val repo: Repo) {
    fun getShopList(): LiveData<List<ShopItem>> = repo.getShopList()

}