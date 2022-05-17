package net.anxdre.poskodigital.view.login

interface LoginView {
    fun showMainMenu()
    fun showLoading()
    fun hideLoading()
    fun saveCredentials(id: String, username: String, kota: String, role: String)
    fun deleteCredentials()
    fun showError(errorMsg:String)
}