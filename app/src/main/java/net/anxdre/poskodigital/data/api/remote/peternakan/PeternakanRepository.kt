package net.anxdre.poskodigital.data.api.remote.peternakan

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItem
import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItemInput

interface PeternakanRepository {
    fun getPeternakanData(tanggal: String): Deferred<PeternakanItem>
    fun updatePeternakanData(dataSet: PeternakanItemInput): Deferred<String>
    fun postPeternakanData(dataSet: PeternakanItemInput): Deferred<String>
    fun deletePeternakanData(id: String): Deferred<String>
}