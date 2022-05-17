package net.anxdre.poskodigital.view.disperindagadmin

import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindag

interface DisperindagAdminView {
    fun showInputData(
        id: String?,
        city: String,
        date: String,
        idSet: String?,
        dataSet: DataDetailPerindag?
    )

    fun showData(dataSource: DataDetailPerindag)
    fun showFilter()
    fun showLoading()
    fun confirmDeleteData(idData: String)
    fun hideLoading()
    fun showAddButton()
    fun initializeData()
    fun hideAddButton()
    fun detachRecyclerview()
    fun showError(msg: String)
    fun hideError()
}