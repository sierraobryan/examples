package com.sierraobryan.movie_db_api.app

import com.sierraobryan.movie_db_api.BuildConfig
import com.sierraobryan.movie_db_api.network.MovieDbAuthenticationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor { chain ->
                    val url = chain.request().url.newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .build()
                    val newRequest = chain.request().newBuilder().url(url).build()
                    chain.proceed(newRequest)
                }
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): MovieDbAuthenticationApi {
        return retrofit.create(MovieDbAuthenticationApi::class.java)
    }

}