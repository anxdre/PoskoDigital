package net.anxdre.poskodigital.data.api.model.bulog

import com.google.gson.annotations.SerializedName


data class Kancab(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: List<X0?>?
) {
    data class X0(
        @SerializedName("id")
        val id: String?,
        @SerializedName("nama")
        val nama: String?
    )
}