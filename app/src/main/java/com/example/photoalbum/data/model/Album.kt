package com.example.photoalbum.data.model

import com.google.gson.annotations.SerializedName

/**
 * Model class for response return by api
 */
data class Album(
    @SerializedName("id")
    val id: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("title")
    val albumTitle: String
)