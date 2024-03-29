package com.example.myshoppingtasks.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopItemDao {
    @Query("SELECT * FROM shop_item")
    fun getAll(): LiveData<List<ShopItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(shopItemDbModel: ShopItemDBModel)

    @Query("DELETE FROM shop_item WHERE id=:shopItemId")
    suspend fun delete(shopItemId: Int)

    @Query("SELECT * FROM shop_item WHERE id=:shopItemId LIMIT 1")
    suspend fun get(shopItemId: Int): ShopItemDBModel
}