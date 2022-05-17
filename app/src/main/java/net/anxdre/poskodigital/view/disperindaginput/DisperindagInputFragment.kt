package net.anxdre.poskodigital.view.disperindaginput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.fragment_disperindag_input.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindag
import net.anxdre.poskodigital.data.api.model.user.Komuditas
import net.anxdre.poskodigital.data.api.remote.disperindag.PerindagResponse
import net.anxdre.poskodigital.utils.OfflineJson
import net.anxdre.poskodigital.utils.SpFactory


/**
 * A simple [Fragment] subclass.
 */
class DisperindagInputFragment : Fragment(), DisperindagInputView {
    private val listKomuditasRaw: Komuditas =
        Gson().fromJson(OfflineJson.komuditas, Komuditas::class.java)
    private var date: String? = null
    private var idAuthCity: String? = null
    private var idData: String? = null
    private var idUser: String? = null
    private var dataSet: DataDetailPerindag? = null
    private var userName: String? = null
    private var mConsumption: String? = null
    private val mApi by lazy { PerindagResponse() }
    private val mPresenter by lazy { DisperindagInputPresenter(mApi, this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        date = arguments?.getString("date")
        idAuthCity = arguments?.getString("city")
        idUser = SpFactory(requireContext()).getAuthId()
        idData = arguments?.getString("id")
        dataSet = arguments?.getSerializable("dataSet") as DataDetailPerindag?
        userName = SpFactory(requireContext()).getAuthName()
        return inflater.inflate(R.layout.fragment_disperindag_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (idData != null) {
            requireActivity().toolbar.title = "Edit Data"
            showData(dataSet!!)
        }
        tv_city_input_perindag.text = "$userName - Id Kota : $idAuthCity"
        tv_date_input_perindag.text = date
        btn_save_input_perindag.setOnClickListener {
            if (fieldValidation()) {
                runBlocking {
                    btn_save_input_perindag.visibility = View.INVISIBLE
                    GlobalScope.launch(Dispatchers.Main) {
                        mPresenter.saveData(
                            getPriceSet(),
                            listKomuditasRaw,
                            idUser!!,
                            idAuthCity!!,
                            date!!,
                            idData,
                            dataSet?.get(0)?.id
                        )
                    }
                }
            }
        }
    }

    override fun showLoading() {
        pb_save_perindag_admin.visibility = View.VISIBLE
        tv_save_desc_perindag.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_save_perindag_admin.visibility = View.GONE
        tv_save_desc_perindag.visibility = View.GONE
    }

    override fun showError(msg: String) {
        MaterialDialog(requireContext()).show {
            title(text = "Error")
            message(text = msg)
        }
    }

    override fun showData(dataSet: DataDetailPerindag) {
        et_beras_medium.setText(dataSet[0].harga)
        et_beras_premium.setText(dataSet[1].harga)
        et_jagung.setText(dataSet[2].harga)
        et_kacang_kedelai.setText(dataSet[3].harga)
        et_cabai_rawit_merah.setText(dataSet[4].harga)
        et_cabai_merah.setText(dataSet[5].harga)
        et_bawang_putih.setText(dataSet[6].harga)
        et_bawang_merah.setText(dataSet[7].harga)
        et_telur.setText(dataSet[8].harga)
        et_daging_ayam.setText(dataSet[9].harga)
        et_daging_sapi_segar.setText(dataSet[10].harga)
        et_ikan_segar.setText(dataSet[11].harga)
        et_minyak_goreng_curah.setText(dataSet[12].harga)
        et_tepung_terigu.setText(dataSet[13].harga)

    }

    private fun fieldValidation(): Boolean {
        return nonEmptyList(
            et_beras_medium,
            et_beras_premium,
            et_jagung,
            et_kacang_kedelai,
            et_cabai_rawit_merah,
            et_cabai_merah,
            et_bawang_putih,
            et_bawang_merah,
            et_telur,
            et_daging_ayam,
            et_daging_sapi_segar,
            et_ikan_segar,
            et_minyak_goreng_curah,
            et_tepung_terigu
        ) { view, msg ->
            view.error = msg
            showError("Pastikan Bidang Telah Terisi Semua")
        }
    }

    private fun getPriceSet(): ArrayList<String> {
        val dataSetHarga = arrayListOf<String>()
        dataSetHarga.add(et_beras_medium.text.toString())
        dataSetHarga.add(et_beras_premium.text.toString())
        dataSetHarga.add(et_jagung.text.toString())
        dataSetHarga.add(et_kacang_kedelai.text.toString())
        dataSetHarga.add(et_cabai_rawit_merah.text.toString())
        dataSetHarga.add(et_cabai_merah.text.toString())
        dataSetHarga.add(et_bawang_putih.text.toString())
        dataSetHarga.add(et_bawang_merah.text.toString())
        dataSetHarga.add(et_telur.text.toString())
        dataSetHarga.add(et_daging_ayam.text.toString())
        dataSetHarga.add(et_daging_sapi_segar.text.toString())
        dataSetHarga.add(et_ikan_segar.text.toString())
        dataSetHarga.add(et_minyak_goreng_curah.text.toString())
        dataSetHarga.add(et_tepung_terigu.text.toString())
        return dataSetHarga
    }

    override fun backNav() {
        findNavController().navigateUp()
    }
}
