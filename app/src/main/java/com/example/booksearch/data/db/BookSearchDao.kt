package com.example.booksearch.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.booksearch.data.model.Book
import kotlinx.coroutines.flow.Flow


@Dao
interface BookSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM books")
    fun getFavoriteBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books")
    fun getFavoritePagingBooks(): PagingSource<Int, Book>


}