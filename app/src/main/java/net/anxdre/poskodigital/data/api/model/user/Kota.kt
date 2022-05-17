package net.anxdre.poskodigital.data.api.model.user

import com.google.gson.annotations.SerializedName


class Kota : ArrayList<KotaItem>()

data class KotaItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("nama_kota")
    val namaKota: String
)