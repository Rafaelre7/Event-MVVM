package com.example.eventmvvm.view.detail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.eventmvvm.data.entity.CheckIn
import com.example.eventmvvm.data.entity.Event
import com.example.eventmvvm.data.repository.CheckInRepository
import com.example.eventmvvm.data.repository.EventRepository
import com.example.eventmvvm.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val repository: EventRepository,
    private val repositoryCheckIn: CheckInRepository
) : ViewModel() {
    private val context = application
    private val _id = MutableLiveData<Int>()

    private val _event = _id.switchMap { id ->
        repository.getEvent(id)
    }
    val event: LiveData<NetworkState<Event>> = _event

    fun start(id: Int) {
        _id.value = id
    }

    fun doCheckIn(body: CheckIn) {
        viewModelScope.launch(Dispatchers.IO) {
            val check = repositoryCheckIn.checkIn(body)
            viewModelScope.launch {
                Toast.makeText(context, "Parabéns, CheckIn Realizado", Toast.LENGTH_LONG).show()
            }
        }
    }

}