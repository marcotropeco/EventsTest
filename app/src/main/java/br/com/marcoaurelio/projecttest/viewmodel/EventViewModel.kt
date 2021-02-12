package br.com.marcoaurelio.projetoTeste.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.marcoaurelio.projetoTeste.model.Events
import br.com.marcoaurelio.projetoTeste.respository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel (private val eventRepository: EventRepository): ViewModel() {

    val events = MutableLiveData<List<Events>>()
    val error = MutableLiveData<Boolean>()
    fun getEvents() {
        viewModelScope.launch {
            try {
                error.value = false
                events.value = eventRepository.getEvents()
            }catch (e: Exception){
                error.value = true
            }
        }
    }
}

