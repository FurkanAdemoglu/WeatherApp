package com.example.kafeinweatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.kafeinweatherapp.BuildConfig
import com.example.kafeinweatherapp.database.WordRoomDatabase
import com.example.kafeinweatherapp.model.remote.ApiService
import com.example.kafeinweatherapp.model.remote.RemoteDataSource
import com.example.kafeinweatherapp.utils.Constants.BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        endPoint: EndPoint
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPoint.url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService,
    ): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideEndPoint(): EndPoint {
        return EndPoint(BASE_URL)
    }
    private fun provideNoAuthOkHttpClient(okHttpClient: OkHttpClient): NoAuthOkHttpClient {
        return NoAuthOkHttpClient(okHttpClient)
    }

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        WordRoomDatabase::class.java,
        "word_database"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: WordRoomDatabase) = db.wordDao()
}

data class EndPoint(val url: String)

data class NoAuthOkHttpClient(val okHttpClient: OkHttpClient)



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthRetrofit