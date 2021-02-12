package br.com.marcoaurelio.projetoTeste.respository

import br.com.marcoaurelio.projetoTeste.model.CheckIn
import br.com.marcoaurelio.projetoTeste.model.Events

interface EventRepository {
    suspend fun getEvents(): List<Events>
    suspend fun getEvent(id: Int): Events
    suspend fun postCheckIn(checkIn: CheckIn)
}