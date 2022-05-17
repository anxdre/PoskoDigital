package net.anxdre.poskodigital.data.api.model.kegiatan

import com.google.gson.annotations.SerializedName


data class KegiatanItem(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: List<X0?>?
) {
    data class X0(
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("desc_gambar")
        val descGambar: String?,
        @SerializedName("deskripsi")
        val deskripsi: String?,
        @SerializedName("gambar")
        val gambar: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("id_users")
        val idUsers: String?,
        @SerializedName("judul")
        val judul: String?,
        @SerializedName("nama_users")
        val namaUsers: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    )
}