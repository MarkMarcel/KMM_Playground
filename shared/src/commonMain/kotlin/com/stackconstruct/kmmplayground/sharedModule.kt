package com.stackconstruct.kmmplayground

import org.koin.dsl.module

val sharedModule = module {
    single {Authenticator()}
}