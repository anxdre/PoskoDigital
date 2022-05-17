package net.anxdre.poskodigital.view.disperindaginput

import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindag

interface DisperindagInputView {
    fun showLoading()
    fun hideLoading()
    fun showData(dataSet: DataDetailPerindag)
    fun backNav()
    fun showError(msg: String)
}