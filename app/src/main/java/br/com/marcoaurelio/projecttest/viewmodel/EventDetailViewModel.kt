package br.com.marcoaurelio.projecttest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.marcoaurelio.projetoTeste.model.CheckIn
import br.com.marcoaurelio.projetoTeste.model.Events
import br.com.marcoaurelio.projetoTeste.respository.EventRepository
import kotlinx.coroutines.launch

class EventDetailViewModel(private val eventRepository: EventRepository) : ViewModel() {

    val eventData = MutableLiveData<Events>()
    val resultCheckinLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val error = MutableLiveData<Boolean>()

    fun getEvent(id: Int) {
        viewModelScope.launch {
            try {
                error.value = false
                eventData.value = eventRepository.getEvent(id)
            } catch (e: Exception) {
                error.value = true
            }
        }
    }

    fun postCheckIn(checkIn: CheckIn) {
        viewModelScope.launch {
            try {
                eventRepository.postCheckIn(checkIn)
                resultCheckinLiveData.value = true
            } catch (ex: Exception) {
                resultCheckinLiveData.value = false
            }

        }
    }
}