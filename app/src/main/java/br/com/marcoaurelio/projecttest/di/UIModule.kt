package br.com.marcoaurelio.projecttest.di

import br.com.marcoaurelio.projecttest.viewmodel.EventDetailViewModel
import br.com.marcoaurelio.projetoTeste.viewmodel.EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { EventViewModel(get()) }
    viewModel { EventDetailViewModel(get()) }
}