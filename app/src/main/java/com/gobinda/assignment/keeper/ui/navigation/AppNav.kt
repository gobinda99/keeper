package com.gobinda.assignment.keeper.ui.navigation

import androidx.navigation.NamedNavArgument

sealed class AppNav(
    val route: String, val arguments: List<NamedNavArgument> = emptyList()
) {

    data object Home : AppNav(route = "home", arguments = emptyList())

    data object Details : AppNav(route = "details", arguments = emptyList())

}
