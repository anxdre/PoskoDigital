package net.anxdre.poskodigital.data.api.model.bulog

data class ItemBulogInput(
    val id: String?,
    val idData: String?,
    val idKomoditas: String,
    val idKancab: String,
    val stok: String,
    val tanggal: String
)