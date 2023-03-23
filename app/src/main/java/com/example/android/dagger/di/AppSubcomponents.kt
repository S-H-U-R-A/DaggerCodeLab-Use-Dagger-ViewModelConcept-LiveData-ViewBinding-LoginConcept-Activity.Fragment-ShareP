package com.example.android.dagger.di

import com.example.android.dagger.login.LoginComponent
import com.example.android.dagger.registration.RegistrationComponent
import com.example.android.dagger.user.UserComponent
import dagger.Module

//A TRAVES DE ESTE MODULO ESTAMOS CONECTANDO EL SUBCOMPONENTE REGISTRATION
//AL COMPONENTE/INTERFAZ/CONTENEDOR PRINCIPAL DE DAGGER
@Module(
    subcomponents = [
        RegistrationComponent::class,
        LoginComponent::class,
        UserComponent::class
    ]
)
class AppSubcomponents {}