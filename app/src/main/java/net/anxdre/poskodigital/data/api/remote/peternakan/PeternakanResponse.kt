package net.anxdre.poskodigital.data.api.remote.peternakan

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.apiPostBasicRequestAsync
import net.anxdre.poskodigital.data.api.apiPostRequestAnyAsync
import net.anxdre.poskodigital.data.api.apiPostRequestStringAsync
import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItem
import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItemInput

class PeternakanResponse : PeternakanRepository {
    override fun getPeternakanData(tanggal: String): Deferred<PeternakanItem> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}data_peternakan_get_by_date",
            PeternakanItem::class.java,
            Pair("tanggal", tanggal),
            Pair(" ", " ")
        ) as Deferred<PeternakanItem>


    override fun updatePeternakanData(dataSet: PeternakanItemInput): Deferred<String> =
        apiPostRequestAnyAsync(
            "${BuildConfig.BASE_URL}put_data_peternakan",
            dataSet
        ) as Deferred<String>

    override fun postPeternakanData(dataSet: PeternakanItemInput): Deferred<String> =
        apiPostRequestAnyAsync(
            "${BuildConfig.BASE_URL}post_data_peternakan",
            dataSet
        ) as Deferred<String>

    override fun deletePeternakanData(id: String): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}delete_data_peternakan",
            Pair("id", id),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " ")
        ) as Deferred<String>

}