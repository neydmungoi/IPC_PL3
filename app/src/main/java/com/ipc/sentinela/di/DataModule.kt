package com.ipc.sentinela.di

import com.ipc.sentinela.data.repository.SentinelRepository
import com.ipc.sentinela.data.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSentinelRepository(): SentinelRepository {
        return SentinelRepository()
    }

    // Nota: UserPreferencesRepository já está anotado com @Inject e @Singleton, 
    // por isso o Hilt consegue encontrá-lo automaticamente desde que o Context 
    // seja fornecido. O Hilt já fornece o Contexto da aplicação por padrão.
}
