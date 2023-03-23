/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.dagger.user

import com.example.android.dagger.storage.Storage
import javax.inject.Inject
import javax.inject.Singleton


private const val REGISTERED_USER = "registered_user"
private const val PASSWORD_SUFFIX = "password"

/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 */
@Singleton
class  UserManager @Inject constructor (
        private val storage: Storage,
        //SI SE USA LA FACTORY DEL SUB-COMPONENT COMO PARÁMETRO
        //DAGGER PROPORCIONARÁ EL SUB-COMPONENTE AL CREAR LA INSTANCIA DE USER MANAGER
        //YA QUE DAGGER SABE COMO CREAR EL SUB-COMPONENTE, CUANDO SE DEFINE EN EL
        //MÓDULO
        private val userComponentFactory: UserComponent.Factory
    )
{


    var userComponent: UserComponent? = null
            private set

    val username: String
        get() = storage.getString( REGISTERED_USER )

    fun registerUser(username: String, password: String) {
        storage.setString(REGISTERED_USER, username)
        storage.setString("$username$PASSWORD_SUFFIX", password)

        //EL USUARIO FUE CREADO POR ENDE, CREAMOS EL SUB-COMPONENTE
        //QUE PERMITE A LAS ACTIVITIES/FRAGMENTS QUE DEPENDEN DEL INICIO DE SESIÓN
        //SER INYECTADAS, PORQUE SI EL USUARIO NO HA INICIADO SESIÓN NO TIENE
        //SENTIDO QUE CREEMOS ESE COMPONENTE
        userJustLoggedIn()
    }

    fun loginUser(username: String, password: String): Boolean {

        val registeredUser = this.username
        if (registeredUser != username) return false

        val registeredPassword = storage.getString("$username$PASSWORD_SUFFIX")
        if (registeredPassword != password) return false

        //EL USUARIO INICIO SESIÓN POR ENDE, CREAMOS EL SUB-COMPONENTE
        //QUE PERMITE A LAS ACTIVITIES/FRAGMENTS QUE DEPENDEN DEL INICIO DE SESIÓN
        //SER INYECTADAS, PORQUE SI EL USUARIO NO HA INICIADO SESIÓN NO TIENE
        //SENTIDO QUE CREEMOS ESE COMPONENTE
        userJustLoggedIn()
        return true
    }

    fun logout() {
        //COMO EL USUARIO CERRO LA SESIÓN NO TIENE
        //SENTIDO TENER EN MEMORIA EL COMPONENTE DE
        //INJECTION "CUANDO EL USUARIO ESTE LOGEADO"
        userComponent = null
    }

    fun unregister() {
        val username = storage.getString(REGISTERED_USER)
        storage.setString(REGISTERED_USER, "")
        storage.setString("$username$PASSWORD_SUFFIX", "")
        //SE LLAMA AL MÉTODO DE CERRAR SESIÓN
        logout()
    }

    private fun userJustLoggedIn() {
        //SE CREA EL COMPONENTE DE USUARIO CUANDO SE LOGEÓ
        userComponent = userComponentFactory.create()
    }

    //MÉTODOS PARA VALIDAR SI EL USUARIO ESTA LOGEADO Ó
    //SI EL USUARIO YA ESTA REGISTRADO
    fun isUserLoggedIn() = ( userComponent != null)

    fun isUserRegistered() = storage.getString(REGISTERED_USER).isNotEmpty()

}
