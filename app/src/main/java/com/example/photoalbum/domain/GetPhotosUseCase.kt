package com.example.photoalbum.domain

import com.example.photoalbum.data.model.Photo
import com.example.photoalbum.data.repository.AlbumRepository
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(private val repository: AlbumRepository) {
    suspend fun invoke(): List<Photo> {
        return repository.getPhotos()
    }
}