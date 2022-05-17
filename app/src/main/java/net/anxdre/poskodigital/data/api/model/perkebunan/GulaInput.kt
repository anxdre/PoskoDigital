package net.anxdre.poskodigital.data.api.model.perkebunan

data class GulaInput(
    val id: String?,
    val idPemakaian: String?, val idPabrik: String,
    val jumlahPabrik: String, val jumlahPetani: String,
    val jumlahPedagang: String, val tanggal: String
)