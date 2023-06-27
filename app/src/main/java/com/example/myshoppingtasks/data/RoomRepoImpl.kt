package com.example.myshoppingtasks.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem

class RoomRepoImpl(application: Application) : Repo {
    private val dao = AppDB.getInstance(application).shopItemDao()
    private val mapper = ShopListMapper()

//    init {
//        for (i in 1 until 19) {
//            addShopItem(ShopItem("Name $i", 1, Random.nextBoolean()))
//        }
//    }


    override fun addShopItem(item: ShopItem) {
        dao.add(mapper.entityToDBModel(item))

    }

    override fun editShopItem(item: ShopItem) {
        dao.add(mapper.entityToDBModel(item))
    }


    override fun removeShopItem(item: ShopItem) {
        dao.delete(item.id)
    }

    override fun getShopItem(id: Int): ShopItem {
        return mapper.dBModelToEntity(dao.get(id))
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return dao.getAll().map {
            mapper.listDBModelsToListEntities(it)
        }

    }

}

