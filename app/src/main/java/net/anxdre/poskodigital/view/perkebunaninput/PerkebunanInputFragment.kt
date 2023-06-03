package net.anxdre.poskodigital.view.perkebunaninput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.android.synthetic.main.fragment_perkebunan_input.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.perkebunan.GulaInput
import net.anxdre.poskodigital.data.api.model.perkebunan.PerkebunanGula
import net.anxdre.poskodigital.data.api.remote.perkebunan.PerkebunanGulaResponse
import net.anxdre.poskodigital.utils.SpFactory

class PerkebunanInputFragment : Fragment(), PerkebunanInputView {
    private val mApi by lazy { PerkebunanGulaResponse() }
    private val mPresenter by lazy { PerkebunanInputPresenter(this, mApi) }
    private var dataSet: PerkebunanGula? = null
    private var idUser: String? = null
    private var idName: String? = null
    private var tanggal: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idUser = SpFactory(requireContext()).getAuthId()
        idName = SpFactory(requireContext()).getAuthName()
        dataSet = arguments?.getParcelable("dataSet") as PerkebunanGula?
        tanggal = arguments?.getString("tanggal")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perkebunan_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataSet?.let { showData(it) }
        tv_date_input_perkebunan.text = tanggal
        tv_city_input_perkebunan.text = "$idName id:$idUser"
        btn_save_perkebunan.setOnClickListener {
            if (fildCheck()) {
                GlobalScope.launch {
                    prepareDataForInput(
                        dataSet?.x0?.get(0)?.id,
                        dataSet?.x0?.get(0)?.idPemakaian
                    )
                }
            }
        }
    }

    override fun showLoading() {
        pb_save_perkebunan_admin.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_save_perkebunan_admin.visibility = View.GONE
    }

    override fun showData(dataSet: PerkebunanGula) {
        et_perkebunan_candi1.setText(dataSet.x0?.get(0)?.jumlahPabrik)
        et_perkebunan_candi2.setText(dataSet.x0?.get(0)?.jumlahPetani)
        et_perkebunan_candi3.setText(dataSet.x0?.get(0)?.jumlahPedagang)

//        et_perkebunan_ptpnx1.setText(dataSet.x0?.get(1)?.jumlahPabrik)
//        et_perkebunan_ptpnx2.setText(dataSet.x0?.get(1)?.jumlahPetani)
//        et_perkebunan_ptpnx3.setText(dataSet.x0?.get(1)?.jumlahPedagang)
//
//        et_perkebunan_ptpnxi1.setText(dataSet.x0?.get(2)?.jumlahPabrik)
//        et_perkebunan_ptpnxi2.setText(dataSet.x0?.get(2)?.jumlahPetani)
//        et_perkebunan_ptpnxi3.setText(dataSet.x0?.get(2)?.jumlahPedagang)

        et_perkebunan_kebon1.setText(dataSet.x0?.get(3)?.jumlahPabrik)
        et_perkebunan_kebon2.setText(dataSet.x0?.get(3)?.jumlahPetani)
        et_perkebunan_kebon3.setText(dataSet.x0?.get(3)?.jumlahPedagang)

        et_perkebunan_raja1.setText(dataSet.x0?.get(4)?.jumlahPabrik)
        et_perkebunan_raja2.setText(dataSet.x0?.get(4)?.jumlahPetani)
        et_perkebunan_raja3.setText(dataSet.x0?.get(4)?.jumlahPedagang)

        et_perkebunan_tebu1.setText(dataSet.x0?.get(5)?.jumlahPabrik)
        et_perkebunan_tebu2.setText(dataSet.x0?.get(5)?.jumlahPetani)
        et_perkebunan_tebu3.setText(dataSet.x0?.get(5)?.jumlahPedagang)

        et_perkebunan_igg1.setText(dataSet.x0?.get(6)?.jumlahPabrik)
        et_perkebunan_igg2.setText(dataSet.x0?.get(6)?.jumlahPetani)
        et_perkebunan_igg3.setText(dataSet.x0?.get(6)?.jumlahPedagang)

        et_perkebunan_rmi1.setText(dataSet.x0?.get(7)?.jumlahPabrik)
        et_perkebunan_rmi2.setText(dataSet.x0?.get(7)?.jumlahPetani)
        et_perkebunan_rmi3.setText(dataSet.x0?.get(7)?.jumlahPedagang)
    }

    override fun backNav() {
        findNavController().navigateUp()
    }

    override fun showError(msg: String) {
        MaterialDialog(requireContext()).show {
            title(text = "Error")
            message(text = msg)
        }
    }

    private suspend fun prepareDataForInput(idDataItem: String?, idPemakaian: String?) {
        val data = arrayListOf<GulaInput>()
        val jobAddData = GlobalScope.launch {
            data.add(
                0,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "1",
                    et_perkebunan_candi1.text.toString(),
                    et_perkebunan_candi2.text.toString(),
                    et_perkebunan_candi3.text.toString(),
                    tanggal!!
                )
            )
            data.add(
                1,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "2",
                    "0",
                    "0",
                    "0",
                    tanggal!!
                )
            )
            data.add(
                2,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "3",
                    "0",
                    "0",
                    "0",
                    tanggal!!
                )
            )
            data.add(
                3,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "4",
                    et_perkebunan_kebon1.text.toString(),
                    et_perkebunan_kebon2.text.toString(),
                    et_perkebunan_kebon3.text.toString(),
                    tanggal!!
                )
            )
            data.add(
                4,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "5",
                    et_perkebunan_raja1.text.toString(),
                    et_perkebunan_raja2.text.toString(),
                    et_perkebunan_raja3.text.toString(),
                    tanggal!!
                )
            )
            data.add(
                5,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "6",
                    et_perkebunan_tebu1.text.toString(),
                    et_perkebunan_tebu2.text.toString(),
                    et_perkebunan_tebu3.text.toString(),
                    tanggal!!
                )
            )
            data.add(
                6,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "7",
                    et_perkebunan_igg1.text.toString(),
                    et_perkebunan_igg2.text.toString(),
                    et_perkebunan_igg3.text.toString(),
                    tanggal!!
                )
            )
            data.add(
                7,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "8",
                    et_perkebunan_rmi1.text.toString(),
                    et_perkebunan_rmi2.text.toString(),
                    et_perkebunan_rmi3.text.toString(),
                    tanggal!!
                )
            )
            data.add(
                8,
                GulaInput(
                    idDataItem,
                    idPemakaian,
                    "9",
                    et_perkebunan_sgn1.text.toString(),
                    et_perkebunan_sgn2.text.toString(),
                    et_perkebunan_sgn3.text.toString(),
                    tanggal!!
                )
            )
        }
        jobAddData.join()
        GlobalScope.launch(Dispatchers.Main) {
            mPresenter.saveData(data, idUser!!, et_total_all_consumption.text.toString(), tanggal!!)
        }
    }

    private fun fildCheck(): Boolean {
        return nonEmptyList(et_total_all_consumption) { view, message ->
            view.error = message
            showError("Pastikan Bidang Telah Terisi Semua")
        }
    }
}
