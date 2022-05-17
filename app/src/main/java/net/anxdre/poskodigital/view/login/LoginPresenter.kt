package net.anxdre.poskodigital.view.login

import net.anxdre.poskodigital.data.api.remote.user.UserRepository

class LoginPresenter(private val mView: LoginView, private val mApi: UserRepository) {

    suspend fun reqLogin(username: String, password: String) {
        mView.showLoading()
        try {
            val response = mApi.loginAsync(username, password).await().x0
            mView.saveCredentials(
                response!!.id!!,
                response.username!!,
                response.idKota!!,
                response.idRole!!
            )
            mView.showMainMenu()
        } catch (e: Exception) {
            mView.showError("Pastikan perangkat anda terhubung ke internet & data yang anda isi benar")
        }
        mView.hideLoading()
    }

}
