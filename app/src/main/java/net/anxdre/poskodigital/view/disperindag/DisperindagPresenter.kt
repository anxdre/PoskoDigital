package net.anxdre.poskodigital.view.disperindag

import net.anxdre.poskodigital.data.api.remote.disperindag.PerindagRepository

class DisperindagPresenter(
    private val mView: DisperindagView,
    private val mApi: PerindagRepository
) {
    suspend fun getLastestHighData(idKomuditas: String, date: String) {
        mView.showLoading()
        try {
            val response = mApi.getHightestValueAsync(idKomuditas, date).await()
            mView.showLastestData(response)
        } catch (e: Exception) {
            mView.showError("Pastikan perangkat anda terhubung ke internet & data yang anda isi benar")
        }
        mView.hideLoading()
    }

    suspend fun getLastHighData(idKomuditas: String, date: String) {
        mView.showLoading()
        try {
            val response = mApi.getHightestValueAsync(idKomuditas, date).await()
            mView.showLastData(response)
        } catch (e: Exception) {
            mView.showError("Pastikan perangkat anda terhubung ke internet & data yang anda isi benar")
        }
        mView.hideLoading()
    }

    suspend fun getLastestLowData(idKomuditas: String, date: String) {
        mView.showLoading()
        try {
            val response = mApi.getLowestValueAsync(idKomuditas, date).await()
            mView.showLastestData(response)
        } catch (e: Exception) {
            mView.showError("Pastikan perangkat anda terhubung ke internet & data yang anda isi benar")
        }
        mView.hideLoading()
    }

    suspend fun getLastLowData(idKomuditas: String, date: String) {
        mView.showLoading()
        try {
            val response = mApi.getLowestValueAsync(idKomuditas, date).await()
            mView.showLastData(response)
        } catch (e: Exception) {
            mView.showError("Pastikan perangkat anda terhubung ke internet & data yang anda isi benar")
        }
        mView.hideLoading()
    }
}