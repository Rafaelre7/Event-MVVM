package com.example.eventmvvm.di

import android.content.Context
import com.example.eventmvvm.data.local.AppDatabase
import com.example.eventmvvm.data.local.EventDao
import com.example.eventmvvm.data.remote.EventRemoteDataSource
import com.example.eventmvvm.data.remote.EventService
import com.example.eventmvvm.data.repository.CheckInRepository
import com.example.eventmvvm.data.repository.EventRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideEventService(retrofit: Retrofit): EventService =
        retrofit.create(EventService::class.java)

    @Singleton
    @Provides
    fun provideEventRemoteDataSource(eventService: EventService) =
        EventRemoteDataSource(eventService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideEventDao(db: AppDatabase) = db.eventDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: EventRemoteDataSource,
        localDataSource: EventDao
    ) =
        EventRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideCheckinRepository(remoteDataSource: EventRemoteDataSource) =
        CheckInRepository(remoteDataSource)

}