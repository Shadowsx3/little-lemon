package com.example.littlelemon.data

import android.view.Menu
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class MenuItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)

@Dao
interface MenuItemDao{
    @Query("SELECT * FROM MenuItem")
    fun getAll(): LiveData<List<MenuItem>>

    @Insert
    fun insertAll(menuItems: List<MenuItem>)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItem) == 0")
    fun isEmpty(): Boolean
}

@Database(entities = [MenuItem::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun menuDao(): MenuItemDao
}