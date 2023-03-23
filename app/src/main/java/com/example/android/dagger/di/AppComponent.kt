package com.example.android.dagger.di

import android.content.Context
import com.example.android.dagger.login.LoginComponent
import com.example.android.dagger.main.MainActivity
import com.example.android.dagger.registration.RegistrationActivity
import com.example.android.dagger.registration.RegistrationComponent
import com.example.android.dagger.registration.enterdetails.EnterDetailsFragment
import com.example.android.dagger.registration.termsandconditions.TermsAndConditionsFragment
import com.example.android.dagger.settings.SettingsActivity
import com.example.android.dagger.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

//SE DEFINE EL GRÁFICO/COMPONENTE/CONTENEDOR
@Singleton
@Component(
    modules = [
        StorageModule::class,
        AppSubcomponents::class,
    ]
)
interface AppComponent {

    //QUIERE DECIR QUE CUANDO QUERAMOS USAR APPCOMPONENT LO
    //DEBEMOS CREAR A PARTIR DE LA FACTORY, PASANDOLE EL ARGUMENTO
    //DE TIPO EN ESTE CASO CONTEXT
    @Component.Factory
    interface Factory{
        fun create( @BindsInstance context: Context ): AppComponent
    }

    //EXPONEMOS EL SUBCOMPONENTE DE REGISTRATION
    fun registrationComponent() : RegistrationComponent.Factory

    //EXPONEMOS EL SUBMODULO DE LOGIN
    fun loginComponent() : LoginComponent.Factory

    //SE EXPONE EL USER MANAGER QUE MANEJA EL SUBCOMPONENTE
    //DE USERCOMPONENT, PARA QUE A TRAVES DE ESTA SE PUEDAN INJECTAR
    //LAS ACTIVIDADES MAINACTIVITY y SETTINGSACTIVITY
    fun userManager(): UserManager

}