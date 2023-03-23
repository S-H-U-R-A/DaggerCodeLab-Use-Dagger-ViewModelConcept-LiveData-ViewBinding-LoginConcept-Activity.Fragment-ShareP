package com.example.android.dagger.login

import com.example.android.dagger.di.ActivityScope
import dagger.Subcomponent

//SE ANOTA CONO @ActivityScope PARA QUE LAS CLASES QUE
//GUARDAN RELACION CON EL LOGIN TENGAN UNA UNICA INSTANCIA

@ActivityScope
@Subcomponent
interface LoginComponent {

    //FÁBRICA QUE SIRVE PARA QUE DAGGER SEPA COMO CREAR ESTE COMPONENTE
    @Subcomponent.Factory
    interface Factory {
        fun create() : LoginComponent
    }

    //Clase que necesita Injection de este SubComponent
    fun inject(activity: LoginActivity)

}