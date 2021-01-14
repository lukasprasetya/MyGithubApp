package com.example.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    var username: String? = null,
    var name: String? = null,
    var photo: String? = null,
    var location: String? = null,
    var company: String? = null,
    var repository: String? = null,
    var follower: String? = null,
    var following: String? = null,
    var isFav: String? = null
) : Parcelable
