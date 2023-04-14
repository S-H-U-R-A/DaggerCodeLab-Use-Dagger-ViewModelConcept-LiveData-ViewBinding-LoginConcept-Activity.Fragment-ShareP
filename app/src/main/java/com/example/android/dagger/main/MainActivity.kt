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

package com.example.android.dagger.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android.dagger.R
import com.example.android.dagger.login.LoginActivity
import com.example.android.dagger.registration.RegistrationActivity
import com.example.android.dagger.settings.SettingsActivity
import com.example.android.dagger.user.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //NUEVO CÓDIGO HILT MIENTRAS SE HACE LA MIGRACIÓN
    /*@InstallIn(SingletonComponent::class)
    @EntryPoint
    interface UserManagerEntryPoint{
        fun userManager() : UserManager
    }*/


/*    @Inject
    lateinit var mainViewModel: MainViewModel*/

    private val mainViewModel: MainViewModel by viewModels()

    //@Inject lateinit var userManager: UserManager

    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val userManager = (application as MyApplication).userManager

        // val userManager = (application as MyApplication).appComponent.userManager()

        //NUEVO CÓDIGO PARA ACCEDER AL USER COMPONENT A PARTIR DE HILT
        //MIENTRAS SE HACE LA MIGRACIÓN TOTAL
/*        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            UserManagerEntryPoint::class.java
        )
        val userManager = entryPoint.userManager()*/

        //SI EL USUARIO NO HA INICIADO SESSIÓN
        if ( !userManager.isUserLoggedIn() ) {
            //SI EL USUARIO NO ESTA REGISTRADO
            if ( !userManager.isUserRegistered() ) {
                //SE ENVIA A LA PÁGINA
                startActivity(
                    Intent(this, RegistrationActivity::class.java)
                )
                //SE FINALIZA LA PAGINA ACTUAL
                finish()
            } else {
                //SE ENVIA A LA PÁGINA LOGIN
                startActivity(Intent(this, LoginActivity::class.java))
                //SE FINALIZA LA PAGINA ACTUAL
                finish()
            }
        } else {

            setContentView(R.layout.activity_main)

            //YA NO SE NECESITA, HILT PROPORCIONA ESTA DEPENDENCIA
            //userManager.userComponent!!.inject(this@MainActivity)

            //SE CONFIGURAN LOS VIEW'S
            setupViews()
        }
    }

    /**
     * Updating unread notifications onResume because they can get updated on SettingsActivity
     */
    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.notifications).text = mainViewModel.notificationsText
    }

    private fun setupViews() {

        findViewById<TextView>(R.id.hello).text = mainViewModel.welcomeText

        findViewById<Button>(R.id.settings).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }

    }
}
