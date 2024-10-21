package com.example.practivemvvm.model

import com.google.gson.annotations.SerializedName

data class DataModelClass(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null,
    @SerializedName("language") var language: String? = null,

    )