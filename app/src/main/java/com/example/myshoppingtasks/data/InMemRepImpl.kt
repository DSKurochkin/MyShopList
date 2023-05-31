package com.example.myshoppingtasks.data

import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

object InMemRepImpl : Repo {
    private val db = mutableMapOf<Int, ShopItem>()
    private var currentId = 0

    private val duplicateMes = "already exist or item has invalid Id"
    private val notExistMes = "not exist."
    private fun errorInIdMes(id: Int, mes: String) = "Item with id = $id $mes"
    private fun checkIdOnExisting(id: Int) {
        if (!db.containsKey(id)) throw RuntimeException(errorInIdMes(id, notExistMes))
    }


    override fun addShopItem(item: ShopItem) {
        currentId++
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = currentId
        }
        if (db.containsKey(item.id)||item.id!= currentId) throw RuntimeException(errorInIdMes(item.id, duplicateMes))
        db.put(item.id, item)
    }

    override fun editShopItem(item: ShopItem) {
        checkIdOnExisting(item.id)
        db[item.id] = item
    }


    override fun removeShopItem(id: Int) {
        checkIdOnExisting(id)
        db.remove(id)
    }

    override fun getShopItem(id: Int): ShopItem {
        checkIdOnExisting(id)
        return db[id] ?: throw RuntimeException("App crash")
    }

    override fun getShopList(): List<ShopItem> {
        val res = ArrayList<ShopItem>()
        res.addAll(db.values)
        return res
    }

}

