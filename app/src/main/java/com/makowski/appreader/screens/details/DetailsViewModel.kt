package com.makowski.appreader.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makowski.appreader.data.Resource
import com.makowski.appreader.model.Item
import com.makowski.appreader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private  val repository: BookRepository): ViewModel() {
    suspend fun getBookInfo(bookId: String): Resource<Item>{
        return repository.getBookInfo(bookId)
    }
}