package com.example.littlelemon

interface Destinations {
    val route: String
}

object Home: Destinations {
    override val route = "Home"
}

object DishDetails: Destinations {
    override val route = "Menu"
    const val argDishId = "dishId"
}

object Onboarding: Destinations {
    override val route = "Onboarding"
}

object Profile: Destinations {
    override val route = "Profile"
}