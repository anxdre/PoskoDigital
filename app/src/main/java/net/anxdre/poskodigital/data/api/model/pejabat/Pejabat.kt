package net.anxdre.poskodigital.data.api.model.pejabat

import com.google.gson.annotations.SerializedName

data class Pejabat(
    @SerializedName("nama")
    val nama: String? = null,
    @SerializedName("imgUrl")
    val imgUrl: String? = null)