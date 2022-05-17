package net.anxdre.poskodigital.view.buloginput

import net.anxdre.poskodigital.data.api.model.bulog.ItemBulog

interface BulogInputView {
    fun showLoading()
    fun hideLoading()
    fun showData(dataSet: ItemBulog)
    fun backNav()
    fun showError(msg: String)
}