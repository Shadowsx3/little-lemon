package com.example.littlelemon.utils

import android.util.Patterns.EMAIL_ADDRESS
import com.example.littlelemon.data.AppDatabase
import com.example.littlelemon.data.MenuItemNetwork
import com.example.littlelemon.data.MenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

fun validateRegData(firstName: String, lastName: String, email: String): Boolean {
    return firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            EMAIL_ADDRESS.matcher(email).matches()
}


suspend fun fetchMenu(url: String): List<MenuItemNetwork> {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    return try {
        httpClient.get(url).body<MenuNetwork>().items
    } finally {
        listOf<MenuItemNetwork>()
    }
}


fun saveMenuToDatabase(database: AppDatabase, menuItemsNetwork: List<MenuItemNetwork>) {
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    database.menuDao().insertAll(menuItemsRoom)
}