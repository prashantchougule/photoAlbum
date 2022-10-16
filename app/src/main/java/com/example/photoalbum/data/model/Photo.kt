package com.example.photoalbum.data.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("albumId")
    val albumId: String,
    @SerializedName("title")
    val photoTitle: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
)
