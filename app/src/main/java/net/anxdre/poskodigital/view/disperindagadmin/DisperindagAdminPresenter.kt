package net.anxdre.poskodigital.view.disperindagadmin

import android.accounts.NetworkErrorException
import net.anxdre.poskodigital.data.api.remote.disperindag.PerindagRepository

class DisperindagAdminPresenter(
    private val mApi: PerindagRepository,
    private val mView: DisperindagAdminView
) {
    suspend fun reqData(idKota: String, date: String) {
        mView.showLoading()
        try {
            val response = mApi.getDataPerindagByKotaAsync(idKota, date).await()
            if (response.status != 0) {
                mView.showAddButton()
                throw Exception("Data Tidak Ditemukan")
            } else {
                val idKotaAdmin = response.x0!!.id!!
                val perindagResponse = mApi.getDataDetailPerindagByDataAsync(idKotaAdmin).await()
                mView.hideAddButton()
                mView.hideError()
                mView.showData(perindagResponse)
            }
        } catch (e: NetworkErrorException) {
            mView.showAddButton()
            mView.showError("Data Tidak ditemukan 🙁")
        }
        mView.hideLoading()
    }

    suspend fun deleteData(idData: String) {
        mApi.deleteDataAsync(idData).await()
        mView.showError("Data Berhasil Dihapus")
        mView.detachRecyclerview()
        mView.showAddButton()
    }

}