package com.example.eventmvvm.data.repository

import com.example.eventmvvm.data.local.EventDao
import com.example.eventmvvm.data.remote.EventRemoteDataSource
import com.example.eventmvvm.utils.performGetOperation
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val remoteDataSource: EventRemoteDataSource,
    private val localDataSource: EventDao
) {
    fun getEvents() = performGetOperation(
        { localDataSource.getEvents() },
        { remoteDataSource.getEvents() },
        { localDataSource.insertAll(it) }
    )
    fun getEvent(id: Int) = performGetOperation(
        { localDataSource.getEvent(id) },
        { remoteDataSource.getEvent(id) },
        { localDataSource.insert(it) }
    )
}