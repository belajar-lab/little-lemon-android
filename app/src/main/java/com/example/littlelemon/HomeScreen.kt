package com.example.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column {
        TopAppBar()
        UpperPanel()
        LowerPanel(navController = navController, dishes = DishRepository.dishes)
    }
}