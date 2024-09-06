package com.gobinda.assignment.keeper.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gobinda.assignment.keeper.ui.home.HomeScreen
import com.gobinda.assignment.keeper.ui.navigation.AppNav

@Composable
fun NavApp(
) {
    val navController: NavHostController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = AppNav.Home.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = AppNav.Home.route) {
            HomeScreen() {
                Toast.makeText(
                    context,
                    "Clicked on ${it.title}",
                    Toast.LENGTH_SHORT
                ).show()
                //navController.navigate(AppNav.Details.route)
            }
        }
        composable(route = AppNav.Details.route) {
            TODO()
        }

    }

}