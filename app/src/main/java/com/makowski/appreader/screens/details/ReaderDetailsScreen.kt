package com.makowski.appreader.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.makowski.appreader.components.ReaderAppBar
import com.makowski.appreader.navigation.ReaderScreens

@Composable
fun DetailsScreen(navController: NavController, bookId: String){
    Scaffold(topBar = {
        ReaderAppBar(title = "Book Details", icon = Icons.Default.ArrowBack, showProfile = false, navController = navController ){
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) {
        Surface(modifier = Modifier
            .padding(3.dp)
            .fillMaxSize()) {
            Column(modifier = Modifier.padding(top = 12.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Book description")
                
            }

        }

    }

}