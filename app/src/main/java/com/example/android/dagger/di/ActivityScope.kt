package com.example.android.dagger.di

import javax.inject.Scope

//ACÁ SE ESTA CREADO UN SCOPE/ÁMBITO
//PERSONALIZADO, EN OTRAS PALABRAS ESTAMOS
//CREADO UNA NUEVA ANOTACIÓN DE SCOPE QUE SE CREARÁ
//EN TIEMPO DE EJECUCIÓN

@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope {}