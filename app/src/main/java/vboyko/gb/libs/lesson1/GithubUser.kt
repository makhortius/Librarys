package vboyko.gb.libs.lesson1

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String = ""
) : Parcelable

