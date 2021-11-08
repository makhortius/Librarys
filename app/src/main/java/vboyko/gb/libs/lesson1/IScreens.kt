package vboyko.gb.libs.lesson1

import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun users(): Screen
    fun oneUser(userName: String, userAvatarUrl: String): Screen
    fun settings() = Unit
}

