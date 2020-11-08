package com.example.hilt_simple.app

import android.app.Application
import androidx.room.Room
import com.example.hilt_simple.data.MainDatabase
import com.example.hilt_simple.data.models.CommitDataDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMainDatabase(application: Application): MainDatabase {
        return Room
            .databaseBuilder(application, MainDatabase::class.java, "MainDatabase.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCommitDataDao(appDatabase: MainDatabase): CommitDataDao {
        return appDatabase.commitDataDao()
    }
}