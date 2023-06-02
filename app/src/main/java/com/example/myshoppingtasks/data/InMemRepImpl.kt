package com.example.myshoppingtasks.data

import androidx.lifecycle.MutableLiveData
import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

object InMemRepImpl : Repo {
    private val db = mutableMapOf<Int, ShopItem>()
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private var currentId = 0

    private val duplicateMes = "already exist or item has invalid Id"
    private val notExistMes = "not exist."
    private fun errorInIdMes(id: Int, mes: String) = "Item with id = $id $mes"
    private fun checkIdOnExisting(id: Int) {
        if (!db.containsKey(id)) throw RuntimeException(errorInIdMes(id, notExistMes))
    }

    init {
        for (i in 1 until 10){
           addShopItem (ShopItem("Name $i", 1, true))
        }
    }


    override fun addShopItem(item: ShopItem) {
        currentId++
        if (item.id == currentId||item.id==ShopItem.UNDEFINED_ID) {
            db.put(currentId, ShopItem(item.name, item.count, item.isEnabled, currentId))
            updateShopList()
        }
        else throw RuntimeException(errorInIdMes(item.id, duplicateMes))

    }

    override fun editShopItem(item: ShopItem) {
        checkIdOnExisting(item.id)
        db[item.id] = item
        updateShopList()
    }


    override fun removeShopItem(id: Int) {
        checkIdOnExisting(id)
        db.remove(id)
        updateShopList()
    }

    override fun getShopItem(id: Int): ShopItem {
        checkIdOnExisting(id)
        return db[id] ?: throw RuntimeException("App crash")
    }

    override fun getShopList(): MutableLiveData<List<ShopItem>> {
        updateShopList()
        return shopListLD
    }

    private fun updateShopList(){
        shopListLD.value = db.values.toList()
    }

}

