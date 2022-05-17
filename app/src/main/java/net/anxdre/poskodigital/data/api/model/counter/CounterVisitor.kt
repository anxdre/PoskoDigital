package net.anxdre.poskodigital.data.api.model.counter

import com.google.gson.annotations.SerializedName


data class CounterVisitor(
    @SerializedName("now")
    val now: String?,
    @SerializedName("total")
    val total: String?
)