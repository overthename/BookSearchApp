package com.example.booksearch.util

import com.example.booksearch.BuildConfig

object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val API_KEY = BuildConfig.bookApiKey
    const val SEARCH_BOOKS_TIME_DELAY = 100L
    const val DATASTORE_NAME = "prefereneces_datastore"
    const val PAGING_SIZE = 15
}