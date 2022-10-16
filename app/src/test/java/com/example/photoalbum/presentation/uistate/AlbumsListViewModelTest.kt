package com.example.photoalbum.presentation.uistate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.photoalbum.data.model.*
import com.example.photoalbum.domain.GetAlbumsUseCase
import com.example.photoalbum.domain.GetPhotosUseCase
import com.example.photoalbum.domain.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumsListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: AlbumsListViewModel
    private val getUsersUseCase = mockk<GetUsersUseCase>()
    private val getAlbumsUseCase = mockk<GetAlbumsUseCase>()
    private val getPhotosUseCase = mockk<GetPhotosUseCase>()
    private val albumsResponse = listOf( Album("1","1","quidem molestiae enim"))
    private val geo = Geo("-37.3159","81.1496")
    private val address = Address("Kulas Light", "Apt. 556","Gwenborough","92998-3874", geo)
    private val company = Company("Romaguera-Crona","Multi-layered client-server neural-net","harness real-time e-markets")
    private val usersResponse = listOf( User("1","Leanne Graham","Bret","Sincere@april.biz",
        address, "1-770-736-8031 x56442","hildegard.org", company))

    private val photosResponse = listOf(Photo("1","1","accusamus beatae ad facilis cum similique qui sunt", "https://via.placeholder.com/600/92c952", "https://via.placeholder.com/150/92c952"))

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getAlbumsUseCase.invoke() } returns albumsResponse
        coEvery { getUsersUseCase.invoke() } returns usersResponse
        coEvery { getPhotosUseCase.invoke() } returns photosResponse
        viewModel = AlbumsListViewModel(getAlbumsUseCase, getUsersUseCase, getPhotosUseCase)
    }

    @Test
    fun `Load method correctly creates the ViewState`() = runTest {
        val values = mutableListOf<AlbumsListUIState>()
        viewModel.viewState.observeForever{
            values.add(it)
        }
        viewModel.fetchAlbums()
        testDispatcher.scheduler.advanceUntilIdle()
        assert(values[0] is AlbumsListUIState.Loading)
        assert(values[1] ==
                AlbumsListUIState.Content(
                    listOf(
                        AlbumItemUIState("accusamus beatae ad facilis cum similique qui sunt","quidem molestiae enim","https://via.placeholder.com/150/92c952","Leanne Graham")
                    )
                ))

    }
}