package com.makowski.appreader.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.makowski.appreader.R
import com.makowski.appreader.components.*
import com.makowski.appreader.model.MBook
import com.makowski.appreader.navigation.ReaderScreens

@Composable
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeScreenViewModel = hiltViewModel()){
    Scaffold(
        topBar = {
                 ReaderAppBar(title = "A.Reader", navController = navController)
        },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            } }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController, viewModel)
        }
    }

}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel){

    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser
    if(!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
        mBook.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "HomeContent: ${listOfBooks.toString()}")
    }
//    val listOfBooks = listOf(
//        MBook(id = "dsda", title = "Wióry", "Jan Kowalski","blah blah bla"),
//        MBook(id = "dsda", title = "Słońce", "Jerzy Kowalski","blah bla"),
//        MBook(id = "dsda", title = "Kwiatek", "Jan Adam",notes = null),
//        MBook(id = "dsda", title = "Wazon", "Jan Psikuta",notes = null),
//        MBook(id = "dsda", title = "Czajnik", "Radek Kowalski","blah la"))
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    else "N/A"
    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n "+" activity right now..")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "profile",
                    modifier = Modifier
                        .clickable { navController.navigate(ReaderScreens.ReaderStatsScreen.name) }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant)
                Text(
                    text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Divider()
            }
            
        }
        ReadingRightNowArea(books = listOf(), navController = navController)
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks){}
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>, onCardPressed: (String)-> Unit) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState)) {
        for (book in listOfBooks){
            ListCard(book){
                onCardPressed(it)
            }
        }
    }
}


@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController){
    ListCard()
}


