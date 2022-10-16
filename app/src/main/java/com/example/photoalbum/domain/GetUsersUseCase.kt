package com.example.photoalbum.domain

import com.example.photoalbum.data.model.User
import com.example.photoalbum.data.repository.AlbumRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: AlbumRepository) {
    suspend fun invoke(): List<User> {
        return repository.getUsers()
    }
}