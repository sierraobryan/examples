package com.example.koin_simple

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.koin_simple.data.MainRepository
import com.example.koin_simple.data.models.Author
import com.example.koin_simple.data.models.Commit
import com.example.koin_simple.data.models.CommitDetails
import com.example.koin_simple.network.LogService
import com.example.koin_simple.ui.main.MainViewModel
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
    private lateinit var mainRepositoryMockk: MainRepository

    @MockK
    private lateinit var logServiceMockk: LogService

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { logServiceMockk.logNetworkAttempt() } just Runs
        every { logServiceMockk.logSuccess() } just Runs
        every { logServiceMockk.logError() } just Runs

        viewModel = MainViewModel(
            mainRepositoryMockk,
            logServiceMockk
        )
    }

    @Test
    fun testInit() {
        assertNotNull(viewModel)
        assertEquals("sierraobryan", viewModel.username.value)
        assertEquals("hackerNews", viewModel.repoName.value)
        assertTrue(viewModel.isLoading.value == false)
        assertTrue(viewModel.isError.value == false)
        assertNull(viewModel.commits.value)
        assertNull(viewModel.fetchCommitsEnabled.value)
    }

    @Test
    fun testListCommitSuccess() {
        every { mainRepositoryMockk.allowNetworkCall.value } returns true
        coEvery {
            mainRepositoryMockk.listCommits(any(), any())
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

        verify { logServiceMockk.logNetworkAttempt() }

        assertNotNull(viewModel.commits.value)
        assertTrue(viewModel.commits.value?.size == 1)

        coVerify { logServiceMockk.logSuccess() }

        assertTrue(viewModel.isError.value == false)
    }

    @Test
    fun testListCommitFailure() {
        every { mainRepositoryMockk.allowNetworkCall.value } returns false

        viewModel.listCommits()

        verify(exactly = 1) { logServiceMockk.logNetworkAttempt() }

        coVerify(exactly = 0) {
            mainRepositoryMockk.listCommits(any(), any())
        }

        coVerify { logServiceMockk.logError() }

        assertNull(viewModel.commits.value)
        assertTrue(viewModel.isError.value == true)
    }
}