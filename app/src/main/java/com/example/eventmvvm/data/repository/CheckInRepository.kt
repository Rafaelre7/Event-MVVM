package com.example.eventmvvm.data.repository

import com.example.eventmvvm.data.entity.CheckIn
import com.example.eventmvvm.data.remote.EventRemoteDataSource
import javax.inject.Inject

class CheckInRepository @Inject constructor(private val remoteDataSource: EventRemoteDataSource) {
    suspend fun checkIn(body: CheckIn): Boolean {
        return try {
            remoteDataSource.checkIn(body)
            return true
        } catch (e: Exception) {
            false
        }
    }
}