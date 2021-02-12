package br.com.marcoaurelio.projecttest.di

import org.koin.core.module.Module

val appModules: List<Module> = listOf(uiModule, dataModule)