package br.com.marcoaurelio.projetoTeste.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HttpService {

    @GET("api/events")
    suspend fun getAllEvents(): List<Events>

    @GET("api/events/{id}")
    suspend fun getEvent(@Path("id") id: Int): Events

    @POST("/checkin")
    suspend fun postCheckIn(@Body checkIn: CheckIn)

}