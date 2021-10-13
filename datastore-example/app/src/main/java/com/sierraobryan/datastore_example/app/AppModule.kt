package com.sierraobryan.datastore_example.app

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesMigration as ProtoSharedPrefsMigration
import androidx.datastore.migrations.SharedPreferencesView
import com.sierraobryan.datastore_example.MemberPreferences
import com.sierraobryan.datastore_example.data.models.ProtoPreferencesSerializer
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

    @Provides
    fun provideProtoDataStore(
        @ApplicationContext context: Context
    ): DataStore<MemberPreferences> {
        return DataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(Constants.PB_FILE_NAME) },
            serializer = ProtoPreferencesSerializer,
            migrations = listOf(
                ProtoSharedPrefsMigration(
                    context,
                    Constants.SHARED_PREFERENCES_NAME
                ) { sharedPrefs: SharedPreferencesView, currentData: MemberPreferences ->
                    currentData.toBuilder().setMember(
                        sharedPrefs.getString(Constants.MEMBER_KEY)
                    ).build()
                }
            )
        )
    }
}