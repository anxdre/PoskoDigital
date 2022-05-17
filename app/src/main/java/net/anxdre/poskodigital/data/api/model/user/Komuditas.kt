package net.anxdre.poskodigital.data.api.model.user

import com.google.gson.annotations.SerializedName


class Komuditas : ArrayList<KomuditasItem>()

data class KomuditasItem(
    @SerializedName("id")
    val id: String?,
    @SerializedName("nama")
    val nama: String?
)