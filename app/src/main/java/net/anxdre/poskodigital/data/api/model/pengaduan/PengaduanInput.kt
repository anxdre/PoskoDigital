package net.anxdre.poskodigital.data.api.model.pengaduan

import com.google.gson.annotations.SerializedName

class PengaduanInput(
    @SerializedName("no_ktp")
    val noKtp: String,
    @SerializedName("nama_lengkap")
    val namaUser: String,
    @SerializedName("keluhan")
    val keluhan: String
)