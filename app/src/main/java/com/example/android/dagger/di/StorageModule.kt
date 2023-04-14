package com.example.android.dagger.di

import com.example.android.dagger.storage.SharedPreferencesStorage
import com.example.android.dagger.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class StorageModule {

    //EL RETORNO ES EL TIPO DE INTERFAZ DE LA QUE QUEREMOS LA IMPLEMENTACIÓN
    //EL PARÁMETRO EN FUNCIÓN INDICA CUAL IMPLEMENTACIÓN DE LA INTERFAZ QUEREMOS
    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage

}