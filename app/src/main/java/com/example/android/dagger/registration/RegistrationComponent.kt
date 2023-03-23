package com.example.android.dagger.registration

import com.example.android.dagger.di.ActivityScope
import com.example.android.dagger.registration.enterdetails.EnterDetailsFragment
import com.example.android.dagger.registration.termsandconditions.TermsAndConditionsFragment
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface RegistrationComponent {

    //ESTA FRABRICA LE SIRVE A DAGGER, PARA SABER COMO CREAR
    //ESTE SUB-COMPONENTE
    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationComponent
    }

    //SE INDICA A DAGGER QUE ESTE ACTIVITY REQUIERE UNA INJECTION
    //PARA LOS CAMPOS/ATRIBUTOS MARCADOS EN LA ACTIVIDAD CON @INJECT
    fun inject(activity: RegistrationActivity)
    fun inject(fragment: EnterDetailsFragment)
    fun inject(fragment: TermsAndConditionsFragment)
}