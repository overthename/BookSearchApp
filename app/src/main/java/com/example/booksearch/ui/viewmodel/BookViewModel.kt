package com.example.booksearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksearch.data.model.Book
import com.example.booksearch.data.repository.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
) : ViewModel() {
    //Room
    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBooks(book)
    }
}