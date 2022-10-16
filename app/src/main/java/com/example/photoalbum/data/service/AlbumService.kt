package com.example.photoalbum.data.service

import com.example.photoalbum.data.model.Album
import com.example.photoalbum.data.model.Photo
import com.example.photoalbum.data.model.User
import retrofit2.http.GET

interface AlbumService {

    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("photos")
    suspend fun getPhotos(): List<Photo>

}