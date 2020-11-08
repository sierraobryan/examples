package com.example.hilt_simple

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hilt_simple.data.MainRepository
import com.example.hilt_simple.data.models.Author
import com.example.hilt_simple.data.models.Commit
import com.example.hilt_simple.data.models.CommitDetails
import com.example.hilt_simple.ui.main.MainViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainViewModelTests {

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var mainRepository: MainRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = MainViewModel(
            mainRepository
        )
    }

    @Test
    fun testInit() {
        assertNotNull(viewModel)
        assertEquals("sierraobryan", viewModel.username.value)
        assertEquals("hackerNews", viewModel.repoName.value)
        assertTrue(viewModel.isLoading.value == false)
        assertNull(viewModel.commits.value)
        assertNull(viewModel.fetchCommitsEnabled.value)
    }

    @Test
    fun testListCommitSuccess() {
        coEvery {
            mainRepository.listCommits(any(), any())
        } returns listOf(
            Commit(
                commit = CommitDetails(
                    message = "test commit",
                    author = Author(
                        name = "test author"
                    )
                ),
                sha = "12345abcd"
            )
        )
        viewModel.listCommits()

        assertNotNull(viewModel.commits)
        assertTrue(viewModel.commits.value?.size == 1)
    }

}