package com.example.photoalbum.di

import com.example.photoalbum.data.repository.AlbumRepository
import com.example.photoalbum.data.repository.AlbumRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideAlbumRepository(repositoryImpl: AlbumRepositoryImpl): AlbumRepository
}