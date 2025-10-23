package com.rhine.travelleandroid.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServerPath

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithErrorHandler
