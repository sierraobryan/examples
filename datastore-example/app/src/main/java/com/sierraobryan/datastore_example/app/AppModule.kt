package com.sierraobryan.datastore_example.app

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sierraobryan.datastore_example.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            Constants.SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideAppDataStore(
        @ApplicationContext context: Context
    ) = PreferenceDataStoreFactory.create(
        migrations = listOf(
            SharedPreferencesMigration(
                context, Constants.SHARED_PREFERENCES_NAME
            )
        ),
        produceFile = { context.preferencesDataStoreFile(Constants.SHARED_PREFERENCES_NAME) }
    )

}