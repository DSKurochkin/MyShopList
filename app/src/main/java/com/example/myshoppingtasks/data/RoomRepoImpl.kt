package com.example.myshoppingtasks.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.myshoppingtasks.domain.Repo
import com.example.myshoppingtasks.domain.ShopItem
import javax.inject.Inject

class RoomRepoImpl @Inject constructor(
    private val dao: ShopItemDao,
    private val mapper: ShopListMapper
) : Repo {


    override suspend fun addShopItem(item: ShopItem) {
        dao.add(mapper.entityToDBModel(item))

    }

    override suspend fun editShopItem(item: ShopItem) {
        dao.add(mapper.entityToDBModel(item))
    }


    override suspend fun removeShopItem(item: ShopItem) {
        dao.delete(item.id)
    }

    override suspend fun getShopItem(id: Int): ShopItem {
        return mapper.dBModelToEntity(dao.get(id))
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return dao.getAll().map {
            mapper.listDBModelsToListEntities(it)
        }

    }

}

