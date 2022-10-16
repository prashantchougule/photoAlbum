package com.example.photoalbum.presentation.uistate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photoalbum.data.model.Album
import com.example.photoalbum.domain.GetAlbumsUseCase
import com.example.photoalbum.domain.GetPhotosUseCase
import com.example.photoalbum.domain.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsListViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val getPhotosUseCase: GetPhotosUseCase
    ): ViewModel() {

    private val _viewState = MutableLiveData<AlbumsListUIState>()
    val viewState: LiveData<AlbumsListUIState>
        get() = _viewState

    fun fetchAlbums(){
        viewModelScope.launch {
            _viewState.postValue(AlbumsListUIState.Loading)
            try {
                coroutineScope {
                    // Data call to fetch news
                    val albumsApiDeferred = async { getAlbumsUseCase.invoke() }
                    val photosApiDeferred = async { getPhotosUseCase.invoke() }
                    val usersApiDeferred = async { getUsersUseCase.invoke() }

                    val albums = albumsApiDeferred.await()
                    val photos = photosApiDeferred.await()
                    val users = usersApiDeferred.await()
                    var albumFinalList: MutableList<AlbumItemUIState> = ArrayList()
                    albumFinalList = albums.map{ album: Album ->
                        val userName = users?.find { album.userId == it.id }!!.name
                        val photo = photos.first {
                            it.albumId == album.id
                        }

                        AlbumItemUIState(photo.photoTitle,album.albumTitle, photo.thumbnailUrl, userName)
                    } as MutableList<AlbumItemUIState>
                    _viewState.postValue(AlbumsListUIState.Content(albumFinalList))
                }

            }catch (ex: Exception){
                _viewState.postValue(AlbumsListUIState.Error)
            }
        }
    }
    //Added code to showcase we can fetch albums with Rx.
    //This need some modifications to the code
//    fun fetchAlbumsWithRx(){
//        var albumFinalList: MutableList<AlbumItemUIState> = ArrayList()
//        _viewState.postValue(AlbumsListUIState.Loading)
//        disposable = Single.zip(
//            getAlbumsUseCase.invoke(),
//            getPhotosUseCase.invoke(),
//            getUsersUseCase.invoke(),
//            object : Function3<List<Album>, List<Photo>, List<User>, Unit> {
//                override fun invoke(albums: List<Album>, photos: List<Photo>, users: List<User>): Unit {
//
//                    albumFinalList = albums.map { album: Album ->
//                        val userName = users.find { album.userId == it.id }
//                        val photo = photos.first {
//                            it.albumId == album.id
//                        }
//                        userName?.let {
//                            AlbumItemUIState(photo.photoTitle,album.albumTitle, photo.thumbnailUrl ,
//                                it.name)
//                        }
//                    } as MutableList<AlbumItemUIState>
//
//                }
//            })
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    _viewState.postValue(AlbumsListUIState.Content(albumFinalList))
//                    Log.i(TAG, "Success Execution") },
//                {
//
//                    _viewState.postValue(AlbumsListUIState.Error)
//                    Log.i(TAG, "Failure in Executing API") }
//            )
//    }
}
