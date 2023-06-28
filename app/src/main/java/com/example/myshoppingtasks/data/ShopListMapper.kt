package com.example.myshoppingtasks.data

import com.example.myshoppingtasks.domain.ShopItem

class ShopListMapper {
    fun entityToDBModel(shopItem: ShopItem) = ShopItemDBModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        isEnabled = shopItem.isEnabled
    )

    fun dBModelToEntity(shopItemDbModel: ShopItemDBModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        isEnabled = shopItemDbModel.isEnabled
    )

    fun listDBModelsToListEntities(list: List<ShopItemDBModel>) = list.map {
        dBModelToEntity(it)
    }
}