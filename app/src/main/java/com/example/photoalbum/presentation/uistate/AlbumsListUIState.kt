package com.example.photoalbum.presentation.uistate


sealed class AlbumsListUIState {
    object Loading : AlbumsListUIState()
    data class Content(val newsList: List<AlbumItemUIState>) : AlbumsListUIState()
    object Error : AlbumsListUIState()
}