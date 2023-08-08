package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.screen.Onboarding
import com.example.littlelemon.screen.Profile
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val sharedPreferences by lazy {
                    getSharedPreferences("LittleLemon", MODE_PRIVATE)
                }
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (sharedPreferences.all.isEmpty()) Onboarding.route else Home.route
                ) {
                    composable(Home.route) { HomeScreen(navController = navController) }
                    composable(Onboarding.route) {
                        Onboarding(
                            sharedPreferences = sharedPreferences,
                            navController = navController
                        )
                    }
                    composable(Profile.route) {
                        Profile(
                            sharedPreferences = sharedPreferences,
                            navController = navController
                        )
                    }
                    composable(
                        route = DishDetails.route + "/{${DishDetails.argDishId}}",
                        arguments = listOf(navArgument(DishDetails.argDishId) {
                            type = NavType.IntType
                        })
                    ) {
                        val id = requireNotNull(it.arguments?.getInt(DishDetails.argDishId)) {
                            "Dish id is null"
                        }
                        DishDetailsScreen(id = id)
                    }
                }
            }
        }
    }
}