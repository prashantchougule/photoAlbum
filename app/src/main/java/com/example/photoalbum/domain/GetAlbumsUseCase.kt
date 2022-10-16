package com.example.photoalbum.domain

import com.example.photoalbum.data.model.Album
import com.example.photoalbum.data.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(private val repository: AlbumRepository) {
    suspend fun invoke(): List<Album> {
        return repository.getAlbums()
    }
}