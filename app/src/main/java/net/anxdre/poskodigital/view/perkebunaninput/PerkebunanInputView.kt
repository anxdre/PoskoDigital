package net.anxdre.poskodigital.view.perkebunaninput

import net.anxdre.poskodigital.data.api.model.perkebunan.PerkebunanGula

interface PerkebunanInputView {
    fun showLoading()
    fun hideLoading()
    fun showData(dataSet: PerkebunanGula)
    fun backNav()
    fun showError(msg: String)
}