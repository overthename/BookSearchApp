package com.example.booksearch.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//json만으로 변환에 실패하기 때문에 field:Json을 붙여줌
@JsonClass(generateAdapter = true)
data class SearchResponse(
    @field:Json(name = "documents")
    val documents: List<Book>,
    @field:Json(name = "meta")
    val meta: Meta
)