package com.example.practivemvvm.model

import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("url") var url: String? = null,
    @SerializedName("user") var user: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("description") var description: String? = null,

    )