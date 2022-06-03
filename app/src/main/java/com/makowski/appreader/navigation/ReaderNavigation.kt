package com.makowski.appreader.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makowski.appreader.screens.SplashScreen
import com.makowski.appreader.screens.details.DetailsScreen
import com.makowski.appreader.screens.home.HomeScreen
import com.makowski.appreader.screens.login.LoginScreen
import com.makowski.appreader.screens.search.BookSearchViewModel
import com.makowski.appreader.screens.search.SearchScreen
import com.makowski.appreader.screens.stats.StatsScreen
import com.makowski.appreader.screens.update.UpdateScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){

        composable(ReaderScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            HomeScreen(navController = navController)
        }
        composable(ReaderScreens.DetailScreen.name){
            DetailsScreen(navController = navController, bookId = "")
        }
        composable(ReaderScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.SearchScreen.name){
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
            StatsScreen(navController = navController)
        }
        composable(ReaderScreens.UpdateScreen.name){
            UpdateScreen(navController = navController)
        }
    }
}