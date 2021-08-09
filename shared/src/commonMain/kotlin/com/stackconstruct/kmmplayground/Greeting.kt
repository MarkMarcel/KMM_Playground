package com.stackconstruct.kmmplayground

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}