package net.anxdre.poskodigital.view.perkebunan

import android.accounts.NetworkErrorException
import net.anxdre.poskodigital.data.api.remote.perkebunan.PerkebunanGulaRepository

class PerkebunanPresenter(
    private val mApi: PerkebunanGulaRepository,
    private val mView: PerkebunanView
) {
    suspend fun getDataByDate(date: String) {
        mView.showLoading()
        try {
            val response = mApi.getDataGulaByDateAsync(date).await()
            getPemakaianByDate(date)
            mView.showData(response)
            mView.hideError()
            mView.hideAddData()
        } catch (e: NetworkErrorException) {
            mView.showError("Data tidak ditemukan 😕")
            mView.showAddData()
        }
        mView.hideLoading()
    }

    private suspend fun getPemakaianByDate(date: String) {
        try {
            val response = mApi.getPemakaianGulaByDateAsync(date).await().x0
            mView.showSubData(response?.produksi, response?.konsumsi, response?.defisit)

        } catch (e: NetworkErrorException) {
            mView.showError("Terjadi masalah, silahkan coba bebera saat lagi 😕")
        }
    }

    suspend fun deleteData(id: String) {
        try {
            mApi.deleteDataGulaAsync(id).await()
            mView.detachData()
            mView.showError("Data Berhasil Dihapus")
            mView.showAddData()
        } catch (e: NetworkErrorException) {
            mView.showError("Terjadi masalah 😕 \nPastikan ponsel anda terhubung internet")
        }
    }
}