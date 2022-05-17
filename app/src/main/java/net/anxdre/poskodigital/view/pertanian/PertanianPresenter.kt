package net.anxdre.poskodigital.view.pertanian

import android.accounts.NetworkErrorException
import net.anxdre.poskodigital.data.api.remote.pertanian.PertanianRepository

class PertanianPresenter(
    private val mApi: PertanianRepository,
    private val mView: PertanianView
) {
    suspend fun getDataByDate(date: String) {
        mView.showLoading()
        try {
            val response = mApi.getItemPertanianByDate(date).await()
            mView.showData(response)
            response.x0?.get(0)?.idData?.let { mView.showIdData(it) }
            mView.hideError()
        } catch (e: NetworkErrorException) {
            mView.showError("Data tidak ditemukan 😕")
            mView.showAddData()
        }
        mView.hideLoading()
    }

    suspend fun deleteData(id: String) {
        try {
            mApi.deleteDataPertanian(id).await()
            mView.detachData()
            mView.showError("Data Berhasil Dihapus")
            mView.showAddData()
        } catch (e: NetworkErrorException) {
            mView.showError("Terjadi Kesalahan 🤕")
        }
    }
}