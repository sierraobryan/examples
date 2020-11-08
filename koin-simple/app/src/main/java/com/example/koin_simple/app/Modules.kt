package com.example.koin_simple.app

import androidx.room.Room
import com.example.koin_simple.data.DatabaseMigrations
import com.example.koin_simple.data.MainDatabase
import com.example.koin_simple.data.MainRepository
import com.example.koin_simple.network.LogService
import com.example.koin_simple.network.GithubService
import com.example.koin_simple.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {

    single {
        GithubService.create("https://api.github.com/")
    }

    single {
        LogService()
    }

    single {
        MainRepository(
            githubService = get()
        )
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            MainDatabase::class.java,
            "MainDatabase.db"
        )
            .apply { DatabaseMigrations.migrations.forEach { addMigrations(it) } }
            .build()
    }

    viewModel {
        MainViewModel(
            mainRepository = get(),
            logService = get()
        )
    }
}