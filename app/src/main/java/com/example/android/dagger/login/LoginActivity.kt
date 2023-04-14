package com.example.android.dagger.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.example.android.dagger.main.MainActivity
import com.example.android.dagger.MyApplication
import com.example.android.dagger.R
import com.example.android.dagger.registration.RegistrationActivity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    //MIENTRAS HACEMOS TODA LA MIGRACIÓN, COMO YA CONFIGURAMOS LOS
    //MODULOS DE LOS SUBCOMPONENTES EN HILT, PODEMOS PROPORCIONARLOS
    //A LAS CLASES QUE USAN ESTOS COMPONENTES CON @ENTRYPOINT
    /*@InstallIn(SingletonComponent::class)
    @EntryPoint
    interface LoginEntryPoint{
        fun loginComponet(): LoginComponent.Factory
    }*/

    //@Inject lateinit var loginViewModel: LoginViewModel

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        // Creates ViewModel and listens for the loginState LiveData
/*        loginViewModel = LoginViewModel(
            (application as MyApplication).userManager
        )*/

        //SE VINCULA EL SUBCOMPONENTE DE DAGGER A ESTA ACTIVIDAD
/*        (application as MyApplication)
            .appComponent.loginComponent()
            .create().inject(this)*/

        //NUEVO CÓDIGO PARA ACCEDER AL LOGIN COMPONENT A PARTIR DE HILT
        //MIENTRAS SE HACE LA MIGRACIÓN TOTAL
/*        val entryPoint = EntryPointAccessors.fromApplication<LoginEntryPoint>(
            applicationContext,
            LoginEntryPoint::class.java
        )
        entryPoint.loginComponet().create().inject(this@LoginActivity)*/


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel.loginState.observe(this, Observer<LoginViewState> { state ->
            when (state) {
                is LoginSuccess -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginError -> errorTextView.visibility = View.VISIBLE
            }
        })

        errorTextView = findViewById(R.id.error)

        setupViews()
    }

    private fun setupViews() {
        val usernameEditText = findViewById<EditText>(R.id.username)
        usernameEditText.isEnabled = false
        usernameEditText.setText(loginViewModel.getUsername())

        val passwordEditText = findViewById<EditText>(R.id.password)
        passwordEditText.doOnTextChanged { _, _, _, _ ->
            errorTextView.visibility = View.INVISIBLE
        }

        findViewById<Button>(R.id.login).setOnClickListener {
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        findViewById<Button>(R.id.unregister).setOnClickListener {
            loginViewModel.unregister()

            val intent = Intent(
                this,
                RegistrationActivity::class.java
            )

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
        }
    }
}

sealed class LoginViewState
object LoginSuccess : LoginViewState()
object LoginError : LoginViewState()
