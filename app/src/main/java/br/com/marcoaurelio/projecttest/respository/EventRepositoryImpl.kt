package br.com.marcoaurelio.projetoTeste.respository

import br.com.marcoaurelio.projetoTeste.model.CheckIn
import br.com.marcoaurelio.projetoTeste.model.Events
import br.com.marcoaurelio.projetoTeste.model.RetrofitBuilder

class EventRepositoryImpl(private val retrofitService: RetrofitBuilder): EventRepository{

    override suspend fun getEvents(): List<Events> = retrofitService.apiService.getAllEvents()

    override suspend fun getEvent(id: Int): Events = retrofitService.apiService.getEvent(id)

    override suspend fun postCheckIn(checkIn: CheckIn) = retrofitService.apiService.postCheckIn(checkIn)
}
