package br.com.marcoaurelio.projecttest.di

import br.com.marcoaurelio.projetoTeste.model.RetrofitBuilder
import br.com.marcoaurelio.projetoTeste.respository.EventRepository
import br.com.marcoaurelio.projetoTeste.respository.EventRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    factory { RetrofitBuilder() }
    factory<EventRepository> { EventRepositoryImpl(get()) }
}