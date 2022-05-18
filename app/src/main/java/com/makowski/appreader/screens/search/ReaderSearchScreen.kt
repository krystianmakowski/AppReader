package com.makowski.appreader.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.makowski.appreader.components.InputField
import com.makowski.appreader.components.ReaderAppBar
import com.makowski.appreader.model.MBook
import com.makowski.appreader.navigation.ReaderScreens

@Preview
@Composable
fun SearchScreen(navController: NavController = NavController(LocalContext.current)){
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) {
        Surface() {
            Column {
                SearchForm(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){
                    //save search state
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController)
            }
        }
    }
}


@Composable
fun BookList(navController: NavController) {
    val listOfBooks = listOf(
        MBook(id = "dsda", title = "Wiory", "Jan Kowalski","blah blah bla"),
        MBook(id = "dsda", title = "Slonce", "Jerzy Kowalski","blah bla"),
        MBook(id = "dsda", title = "Kwiatek", "Jan Adam",notes = null),
        MBook(id = "dsda", title = "Wazon", "Jan Psikuta",notes = null),
        MBook(id = "dsda", title = "Czajnik", "Radek Kowalski","blah la"))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(items = listOfBooks){ book ->
            BookRow(book, navController)
        }

    }
}

@Composable
fun BookRow(book: MBook, navController: NavController) {
    Card(modifier = Modifier
        .clickable { }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top) {
            val imageUrl = "https://www.w3schools.com/howto/img_forest.jpg"
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "image book",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )
            Column() {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(text = "Author: ${book.authors}", overflow = TextOverflow.Clip, style = MaterialTheme.typography.caption)
            }
        }
    
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}){
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember (searchQueryState.value){ searchQueryState.value.trim().isNotEmpty() }
        InputField(
            valueState = searchQueryState,
            labelID = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }

}