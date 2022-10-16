package com.example.photoalbum.data.repository

import com.example.photoalbum.data.model.Album
import com.example.photoalbum.data.model.Photo
import com.example.photoalbum.data.model.User
import com.example.photoalbum.data.service.AlbumService
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(val api: AlbumService): AlbumRepository {

    override suspend fun getAlbums(): List<Album> {
        return api.getAlbums()
    }

    override suspend fun getPhotos(): List<Photo> {
        return api.getPhotos()
    }

    override suspend fun getUsers(): List<User> {
        return api.getUsers()
    }
}