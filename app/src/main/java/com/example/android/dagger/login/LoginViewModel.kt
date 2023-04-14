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

package com.example.android.dagger.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.dagger.user.UserManager
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

//NO LO ANOTAMOS CON EL @ACTIVITYSCOPE PORQUE COMO SOLO VA A SER
//USADO POR LA ACTIVIDAD NO NECESITAMOS COMPARTIRLO MIENTRAS
//ESTA ACTIVIDAD ESTE VIVA
@ActivityScoped
class LoginViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginViewState>()
    val loginState: LiveData<LoginViewState>
        get() = _loginState

    fun login(username: String, password: String) {
        if (userManager.loginUser(username, password)) {
            _loginState.value = LoginSuccess
        } else {
            _loginState.value = LoginError
        }
    }

    fun unregister() {
        userManager.unregister()
    }

    fun getUsername(): String = userManager.username
}
