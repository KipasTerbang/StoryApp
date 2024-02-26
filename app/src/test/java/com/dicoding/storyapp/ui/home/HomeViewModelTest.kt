package com.dicoding.storyapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.core.domain.usecase.story.StoryUseCase
import com.dicoding.storyapp.core.domain.usecase.storypaging.StoryPagingUseCase
import com.dicoding.storyapp.core.domain.usecase.user.UserUseCase
import com.dicoding.storyapp.home.HomeViewModel
import com.dicoding.storyapp.ui.StoriesHomeAdapter
import com.dicoding.storyapp.utils.DataDummy
import com.dicoding.storyapp.utils.MainDispatcherRule
import com.dicoding.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var userUseCase: UserUseCase
    @Mock
    private lateinit var storyUseCase: StoryUseCase
    @Mock
    private lateinit var storyPagingUseCase: StoryPagingUseCase
    private lateinit var viewModel: HomeViewModel
    private lateinit var token: String


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel(userUseCase, storyUseCase, storyPagingUseCase)
        token = "dummyToken"
    }

    @Test
    fun `when get all stories should not null and return data`() = runTest {
        val dummyStories = DataDummy.generateDummyStoryEntity()
        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStories)
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = data
        `when`(storyPagingUseCase.getAllStories(token)).thenReturn(expectedStory.asFlow())
        val actualStory: PagingData<Story> = viewModel.getAllStories(token).getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesHomeAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStories.size, differ.snapshot().size)
        assertEquals(dummyStories[0], differ.snapshot()[0])
    }

    @Test
    fun `when get all stories should return no data`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = data
        `when`(storyPagingUseCase.getAllStories(token)).thenReturn(expectedStory.asFlow())
        val actualStory: PagingData<Story> = viewModel.getAllStories(token).getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesHomeAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)
        assertEquals(0, differ.snapshot().size)
    }

    class StoryPagingSource : PagingSource<Int, LiveData<List<Story>>>() {
        companion object {
            fun snapshot(items: List<Story>): PagingData<Story> {
                return PagingData.from(items)
            }
        }

        @Suppress("SameReturnValue")
        override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
            return LoadResult.Page(emptyList(), 0, 1)
        }
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}