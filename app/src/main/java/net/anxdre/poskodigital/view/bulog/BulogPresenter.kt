package net.anxdre.poskodigital.view.bulog

import android.accounts.NetworkErrorException
import net.anxdre.poskodigital.data.api.model.bulog.BulogTotal
import net.anxdre.poskodigital.data.api.remote.bulog.BulogRepository

class BulogPresenter(
    private val mApi: BulogRepository,
    private val mView: BulogView
) {
    suspend fun getDataByDate(date: String, kancab: String) {
        mView.showLoading()
        try {
            val response = mApi.getItemBulogByDate(date, kancab).await()
            mView.showData(response)
            mView.hideError()
            mView.hideAddData()
        } catch (e: NetworkErrorException) {
            mView.showError("Data tidak ditemukan 😕")
            mView.showAddData()
        }
        mView.hideLoading()
    }

    suspend fun getTotalJatim(date: String): BulogTotal {
        var response: BulogTotal = BulogTotal("0", "0", "0", "0", "0")
        try {
            response = mApi.getTotalJatimByDate(date).await()
            return response
        } catch (e: NetworkErrorException) {
            mView.showError("Data tidak ditemukan 😕")
            mView.showAddData()
        }
        return response
    }

    suspend fun deleteData(id: String) {
        try {
            mApi.deleteDataBulog(id).await()
            mView.detachData()
            mView.showError("Data Berhasil Dihapus")
            mView.showAddData()
        } catch (e: NetworkErrorException) {
            mView.showError("Terjadi masalah 😕 \nPastikan ponsel anda terhubung internet")
        }
    }
}