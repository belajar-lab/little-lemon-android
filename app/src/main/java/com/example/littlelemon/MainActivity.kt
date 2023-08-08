package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.littlelemon.screen.Home
import com.example.littlelemon.screen.Onboarding
import com.example.littlelemon.screen.Profile
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

class MainActivity : ComponentActivity() {
    private val sharedPreferences by lazy {
        getSharedPreferences("LittleLemon", MODE_PRIVATE)
    }

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = "database"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (sharedPreferences.all.isEmpty()) Onboarding.route else Home.route
                ) {
                    composable(Home.route) { Home(navController = navController) }
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

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response: HttpResponse =
            httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
        return response.body<MenuNetwork>().menu
    }

    private fun saveMenuToDatabase(menuItemNetwork: List<MenuItemNetwork>) {
        val menuItemRoom = menuItemNetwork.map { it.toMenuItemRoom() }
        return database.menuItemDao().insertAll(*menuItemRoom.toTypedArray())
    }
}