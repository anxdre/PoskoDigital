package net.anxdre.poskodigital.data.api.remote.perkebunan

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.data.api.model.perkebunan.PemakaianGula
import net.anxdre.poskodigital.data.api.model.perkebunan.PerkebunanGula

interface PerkebunanGulaRepository {
    fun getDataGulaByDateAsync(tanggal: String): Deferred<PerkebunanGula>
    fun getPemakaianGulaByDateAsync(tanggal: String): Deferred<PemakaianGula>
    fun deleteDataGulaAsync(idPemakaian: String): Deferred<String>
    fun updateDataGulaByDateAsync(
        id: String,
        idPemakaian: String,
        idPabrik: String,
        jumlahPabrik: String,
        jumlahPetani: String,
        jumlahPedagang: String,
        tanggal: String
    ): Deferred<String>

    fun postDataGulaByDateAsync(
        id_pemakaian: String,
        idPabrik: String,
        jumlahPabrik: String,
        jumlahPetani: String,
        jumlahPedagang: String,
        tanggal: String
    ): Deferred<String>

    fun updatePemakaianGulaByDateAsync(
        id: String,
        idUser: String,
        konsumsi: String,
        tanggal: String
    ): Deferred<String>

    fun postPemakaianGulaByDateAsync(
        idUser: String,
        konsumsi: String,
        tanggal: String
    ): Deferred<String>
}