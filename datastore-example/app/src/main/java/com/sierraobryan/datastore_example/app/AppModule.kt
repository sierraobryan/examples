package com.sierraobryan.datastore_example.app

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.migrations.SharedPreferencesView
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.createDataStore
import com.sierraobryan.datastore_example.MemberPreferences
import com.sierraobryan.datastore_example.data.models.ProtoPreferencesSerializer
import com.sierraobryan.datastore_example.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

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
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.createDataStore(
            name = Constants.SHARED_PREFERENCES_NAME,
            migrations = listOf(
                SharedPreferencesMigration(
                    context,
                    Constants.SHARED_PREFERENCES_NAME
                )
            ),
        )
    }

    @Provides
    fun provideProtoDataStore(
        @ApplicationContext context: Context
    ): DataStore<MemberPreferences> {
        return context.createDataStore(
            fileName = Constants.PB_FILE_NAME,
            serializer = ProtoPreferencesSerializer,
            migrations = listOf(
                androidx.datastore.migrations.SharedPreferencesMigration(
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