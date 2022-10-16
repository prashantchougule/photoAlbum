package com.example.photoalbum.data.repository

import com.example.photoalbum.data.model.Album
import com.example.photoalbum.data.model.Photo
import com.example.photoalbum.data.model.User

interface AlbumRepository {
     suspend fun getAlbums(): List<Album>
     suspend fun getPhotos(): List<Photo>
     suspend fun getUsers(): List<User>
}