package net.anxdre.poskodigital.data.api.remote.disperindag

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.apiPostBasicRequestAsync
import net.anxdre.poskodigital.data.api.apiPostRequestStringAsync
import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindag
import net.anxdre.poskodigital.data.api.model.disperindag.DataPerindag

class PerindagResponse : PerindagRepository {
    override fun getHightestValueAsync(
        idKomoditas: String,
        date: String
    ): Deferred<DataDetailPerindag> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}item_get_by_highest?",
            DataDetailPerindag::class.java,
            Pair("id_komoditas", idKomoditas),
            Pair("id_tanggal", date)
        ) as Deferred<DataDetailPerindag>

    override fun getLowestValueAsync(
        idKomoditas: String,
        date: String
    ): Deferred<DataDetailPerindag> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}item_get_by_lowest?",
            DataDetailPerindag::class.java,
            Pair("id_komoditas", idKomoditas),
            Pair("id_tanggal", date)
        ) as Deferred<DataDetailPerindag>

    override fun getDataPerindagByKotaAsync(idKota: String, date: String): Deferred<DataPerindag> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}data_get_kota_tanggal?",
            DataPerindag::class.java,
            Pair("id_kota", idKota),
            Pair("tanggal", date)
        ) as Deferred<DataPerindag>

    override fun getDataDetailPerindagByDataAsync(idData: String): Deferred<DataDetailPerindag> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}item_get_by_data?",
            DataDetailPerindag::class.java,
            Pair("id_data", idData),
            Pair(" ", " ")
        ) as Deferred<DataDetailPerindag>

    override fun deleteDataAsync(idData: String): Deferred<String> = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}delete_data_perindag?",
        Pair("id", idData),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair("", " ")
    ) as Deferred<String>

    override fun saveDataAsync(idUser: String, idCity: String, date: String): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}post_data_perindag?",
            Pair("id_users", idUser),
            Pair("id_kota", idCity),
            Pair("tanggal", date),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair("", " ")
        ) as Deferred<String>

    override fun saveItemAsync(
        idData: String,
        idCity: String,
        idItem: String,
        price: String,
        date: String
    ): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}post_item_perindag?",
            Pair("id_data", idData),
            Pair("id_kota", idCity),
            Pair("id_komoditas", idItem),
            Pair("harga", price),
            Pair("tanggal", date),
            Pair("", ""),
            Pair("", "")
        ) as Deferred<String>

    override fun updateItemAsync(
        idDataSet: String,
        idData: String,
        idCity: String,
        idItem: String,
        price: String,
        date: String
    ): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}put_item_perindag?",
            Pair("id", idDataSet),
            Pair("id_data", idData),
            Pair("id_kota", idCity),
            Pair("id_komoditas", idItem),
            Pair("harga", price),
            Pair("tanggal", date),
            Pair("", " ")
        ) as Deferred<String>
}