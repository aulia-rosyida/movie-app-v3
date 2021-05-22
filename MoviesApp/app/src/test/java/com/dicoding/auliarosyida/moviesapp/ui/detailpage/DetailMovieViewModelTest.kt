package com.dicoding.auliarosyida.moviesapp.ui.detailpage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.load.engine.Resource
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.utils.DataMovies
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DataMovies.generateMovies()[0]
    private val tempMovieId = dummyMovie.movieId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var detailObserver: Observer<ResourceWrapData<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
        viewModel.setSelectedDetail(tempMovieId)
    }

    @Test
    fun testGetMovieEntity() {
        val movie = MutableLiveData<ResourceWrapData<MovieEntity>>()
        movie.value = ResourceWrapData.success(dummyMovie)

        `when`(movieRepository.getDetailMovie(tempMovieId)).thenReturn(movie)
        viewModel.detailMovie.observeForever(detailObserver)
        verify(detailObserver).onChanged(movie.value)
    }

    @Test
    fun testSetFavoriteMovie() {
        val dummyTestMovie: ResourceWrapData<MovieEntity> = ResourceWrapData.success(dummyMovie)
        val movie: MutableLiveData<ResourceWrapData<MovieEntity>> = MutableLiveData<ResourceWrapData<MovieEntity>>()
        movie.value = dummyTestMovie

        `when`(movieRepository.getDetailMovie(tempMovieId)).thenReturn(movie)
        viewModel.detailMovie.observeForever(detailObserver)

        dummyTestMovie.data?.let {
            doNothing().`when`(movieRepository).setFavoriteMovie(it, true)
            viewModel.setFavoriteMovie()
            verify(movieRepository).setFavoriteMovie(it, true)
        }
    }
}