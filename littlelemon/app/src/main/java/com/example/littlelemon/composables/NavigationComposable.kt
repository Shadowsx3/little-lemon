package com.example.littlelemon.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.navigation.Home
import com.example.littlelemon.navigation.Onboarding
import com.example.littlelemon.navigation.Profile

@Composable
fun NavigationComposable(context: Context, navController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    var startDestination = Onboarding.route

    if (sharedPreferences.getBoolean("logged", false)) {
        startDestination = Home.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(sharedPreferences, navController)
        }
        composable(Home.route) {
            Home(navController)
        }
        composable(Profile.route) {
            Profile(sharedPreferences, navController)
        }
    }
}