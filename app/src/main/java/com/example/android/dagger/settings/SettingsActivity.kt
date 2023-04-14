package com.example.android.dagger.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android.dagger.R
import com.example.android.dagger.login.LoginActivity
import com.example.android.dagger.user.UserManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

/*    @Inject
    lateinit var settingsViewModel: SettingsViewModel*/

    private val settingsViewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {

        //SE LE DICE AL USER COMPONENT QUE ESTA CLASE REQUIERE INYECCIÓN
        //POR PARTE DE DAGGER
        /*val userManager = (application as MyApplication).appComponent.userManager()
        userManager.userComponent!!.inject(this@SettingsActivity)*/

        //NUEVO CÓDIGO HILT MIENTRAS SE HACE LA MIGRACIÓN
        /*val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            MainActivity.UserManagerEntryPoint::class.java
        )
        entryPoint.userManager()*/

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupViews()
    }

    private fun setupViews() {

        findViewById<Button>(R.id.refresh).setOnClickListener {
            settingsViewModel.refreshNotifications()
        }

        findViewById<Button>(R.id.logout).setOnClickListener {
            settingsViewModel.logout()

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }
    }

}
