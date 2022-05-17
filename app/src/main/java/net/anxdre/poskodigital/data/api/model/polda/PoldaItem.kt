package net.anxdre.poskodigital.data.api.model.polda

import com.google.gson.annotations.SerializedName


data class PoldaItem(
    @SerializedName("status")
    val status: Int,
    @SerializedName("0")
    val x0: List<X0>
) {
    data class X0(
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("henti_lidik")
        val hentiLidik: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("id_data")
        val idData: String,
        @SerializedName("id_satuan")
        val idSatuan: String,
        @SerializedName("id_users")
        val idUsers: String,
        @SerializedName("jumlah_lp")
        val jumlahLp: String,
        @SerializedName("limpah")
        val limpah: String,
        @SerializedName("nama_satuan")
        val namaSatuan: String,
        @SerializedName("nama_users")
        val namaUsers: String,
        @SerializedName("p_21")
        val p21: String,
        @SerializedName("sisa_lp")
        val sisaLp: String,
        @SerializedName("sp_3")
        val sp3: String,
        @SerializedName("tahun_bulan")
        val tahunBulan: String,
        @SerializedName("total_selra")
        val totalSelra: String,
        @SerializedName("updated_at")
        val updatedAt: String?
    )
}