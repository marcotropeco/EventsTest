package br.com.marcoaurelio.projetoTeste.model


class ApiHelper(private val httpService: HttpService) {
    suspend fun getEvent(id: Int) = httpService.getEvent(id)
    suspend fun getAllEvents() = httpService.getAllEvents()
}