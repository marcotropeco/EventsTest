package br.com.marcoaurelio.projetoTeste.model


data class Events(
    val id: String?,
    val title: String,
    val description: String,
    val longitude: String,
    val latitude: String?,
    val date: String,
    val price: Double,
    val image: String
)