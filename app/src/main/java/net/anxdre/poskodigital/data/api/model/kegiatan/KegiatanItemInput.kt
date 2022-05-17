package net.anxdre.poskodigital.data.api.model.kegiatan

import com.google.gson.annotations.SerializedName

data class KegiatanItemInput(
    @SerializedName("id_users")
    val idUsers: String,
    @SerializedName("nama_users")
    val namaUser: String,
    @SerializedName("judul")
    val judul: String,
    @SerializedName("deskripsi")
    val desc: String,
    @SerializedName("desc_gambar")
    val descGambar: String
)