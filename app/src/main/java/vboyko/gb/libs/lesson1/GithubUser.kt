package vboyko.gb.libs.lesson1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val login: String,
    val id: Int
) : Parcelable

