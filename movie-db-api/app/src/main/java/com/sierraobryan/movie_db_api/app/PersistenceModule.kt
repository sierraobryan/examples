package com.sierraobryan.movie_db_api.app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class PersistenceModule {

    @Provides
    fun provideDataStore(
            @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.createDataStore(
                name = "movie_db_preferences",
                migrations = listOf()
        )
    }

}