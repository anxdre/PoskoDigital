package net.anxdre.poskodigital.view.disperindag

import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindagItem

interface DisperindagView {
    fun showMainMenu()
    fun showLoading()
    fun hideLoading()
    fun showLastestData(dataSource: List<DataDetailPerindagItem>)
    fun showLastData(dataSource: List<DataDetailPerindagItem>)
    fun showFilter()
    fun showError(errorMsg: String)
}