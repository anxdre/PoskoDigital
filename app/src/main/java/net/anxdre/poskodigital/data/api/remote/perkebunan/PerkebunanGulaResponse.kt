package net.anxdre.poskodigital.data.api.remote.perkebunan

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.apiPostManyRequestTypeAsync
import net.anxdre.poskodigital.data.api.apiPostRequestStringAsync
import net.anxdre.poskodigital.data.api.model.perkebunan.PemakaianGula
import net.anxdre.poskodigital.data.api.model.perkebunan.PerkebunanGula

class PerkebunanGulaResponse : PerkebunanGulaRepository {
    override fun getDataGulaByDateAsync(tanggal: String): Deferred<PerkebunanGula> =
        apiPostManyRequestTypeAsync(
            "${BuildConfig.BASE_URL}data_gula_get_by_date",
            Pair("tanggal", tanggal),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            PerkebunanGula::class.java
        ) as Deferred<PerkebunanGula>

    override fun getPemakaianGulaByDateAsync(tanggal: String): Deferred<PemakaianGula> =
        apiPostManyRequestTypeAsync(
            "${BuildConfig.BASE_URL}pemakaian_gula_get_by_date",
            Pair("tanggal", tanggal),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            PemakaianGula::class.java
        ) as Deferred<PemakaianGula>

    override fun deleteDataGulaAsync(idPemakaian: String): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}delete_data_gula",
            Pair("id", idPemakaian),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " ")
        ) as Deferred<String>


    override fun postDataGulaByDateAsync(
        id_pemakaian: String,
        idPabrik: String,
        jumlahPabrik: String,
        jumlahPetani: String,
        jumlahPedagang: String,
        tanggal: String
    ): Deferred<String> = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}post_data_gula",
        Pair("id_pemakaian", id_pemakaian),
        Pair("id_pabrik", idPabrik),
        Pair("jumlah_pabrik", jumlahPabrik),
        Pair("jumlah_petani", jumlahPetani),
        Pair("jumlah_pedagang", jumlahPedagang),
        Pair("tanggal", tanggal),
        Pair(" ", " ")
    ) as Deferred<String>

    override fun updateDataGulaByDateAsync(
        id: String,
        idPemakaian: String,
        idPabrik: String,
        jumlahPabrik: String,
        jumlahPetani: String,
        jumlahPedagang: String,
        tanggal: String
    ): Deferred<String> = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}put_data_gula",
        Pair("id", id),
        Pair("id_pemakaian", idPemakaian),
        Pair("id_pabrik", idPabrik),
        Pair("jumlah_pabrik", jumlahPabrik),
        Pair("jumlah_petani", jumlahPetani),
        Pair("jumlah_pedagang", jumlahPedagang),
        Pair("tanggal", tanggal)
    ) as Deferred<String>

    override fun updatePemakaianGulaByDateAsync(
        id: String,
        idUser: String,
        konsumsi: String,
        tanggal: String
    ): Deferred<String> = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}put_data_pemakaian",
        Pair("id", id),
        Pair("id_users", idUser),
        Pair("konsumsi", konsumsi),
        Pair("tanggal", tanggal),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " ")
    ) as Deferred<String>

    override fun postPemakaianGulaByDateAsync(
        idUser: String,
        konsumsi: String,
        tanggal: String
    ): Deferred<String> = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}post_data_pemakaian",
        Pair("id_users", idUser),
        Pair("konsumsi", konsumsi),
        Pair("tanggal", tanggal),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " ")
    ) as Deferred<String>

}