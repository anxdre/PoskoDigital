package net.anxdre.poskodigital.view.buloginput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.android.synthetic.main.fragment_bulog_input.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.bulog.ItemBulog
import net.anxdre.poskodigital.data.api.model.bulog.ItemBulogInput
import net.anxdre.poskodigital.data.api.remote.bulog.BulogResponse
import net.anxdre.poskodigital.utils.SpFactory

/**
 * A simple [Fragment] subclass.
 */
class BulogInput : Fragment(), BulogInputView {
    private val mApi by lazy { BulogResponse() }
    private val mPresenter by lazy { BulogInputPresenter(mApi, this) }
    private var dataSet: ItemBulog? = null
    private var idUser: String? = null
    private var idName: String? = null
    private var idKanCab: String? = null
    private var tanggal: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idUser = SpFactory(requireContext()).getAuthId()
        idName = SpFactory(requireContext()).getAuthName()
        dataSet = arguments?.getParcelable("dataSet") as ItemBulog?
        tanggal = arguments?.getString("tanggal")
        idKanCab = arguments?.getString("idKancab")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bulog_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataSet?.let { showData(it) }
        tv_date_input_bulog.text = tanggal
        tv_city_input_bulog.text = "$idName id:$idUser"
        btn_save_bulog.setOnClickListener {
            if (isCorrected()) {
                runBlocking {
                    btn_save_bulog.visibility = View.INVISIBLE
                    GlobalScope.launch(Dispatchers.Main) {
                        prepareDataForInput(dataSet?.x0?.get(0)?.idData)
                    }
                }
            }
        }


    }

    override fun showLoading() {
        pb_save_bulog_admin.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_save_bulog_admin.visibility = View.GONE
    }

    override fun showData(dataSet: ItemBulog) {
        et_beras_bulog.setText(dataSet.x0?.get(0)?.stok)
        et_gula_bulog.setText(dataSet.x0?.get(1)?.stok)
        et_jagung_bulog.setText(dataSet.x0?.get(2)?.stok)
        et_minyak_bulog.setText(dataSet.x0?.get(3)?.stok)
        et_tepung_bulog.setText(dataSet.x0?.get(4)?.stok)

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

    private fun isCorrected(): Boolean {
        return nonEmptyList(
            et_beras_bulog,
            et_gula_bulog,
            et_jagung_bulog,
            et_minyak_bulog,
            et_tepung_bulog
        ) { view, msg ->
            view.error = msg
            showError("Pastikan Bidang Telah Terisi Semua")
        }
    }

    private suspend fun prepareDataForInput(idData: String?) {
        val data = arrayListOf<ItemBulogInput>()
        val jobAddData = GlobalScope.launch {
            data.add(
                0, ItemBulogInput(
                    dataSet?.x0?.get(0)?.id,
                    idData,
                    "15",
                    idKanCab!!,
                    et_beras_bulog.text.toString(),
                    tanggal!!
                )
            )

            data.add(
                0, ItemBulogInput(
                    dataSet?.x0?.get(1)?.id,
                    idData,
                    "14",
                    idKanCab!!,
                    et_gula_bulog.text.toString(),
                    tanggal!!
                )
            )

            data.add(
                0, ItemBulogInput(
                    dataSet?.x0?.get(2)?.id,
                    idData,
                    "3",
                    idKanCab!!,
                    et_jagung_bulog.text.toString(),
                    tanggal!!
                )
            )

            data.add(
                0, ItemBulogInput(
                    dataSet?.x0?.get(3)?.id,
                    idData,
                    "18",
                    idKanCab!!,
                    et_minyak_bulog.text.toString(),
                    tanggal!!
                )
            )

            data.add(
                0, ItemBulogInput(
                    dataSet?.x0?.get(4)?.id,
                    idData,
                    "13",
                    idKanCab!!,
                    et_tepung_bulog.text.toString(),
                    tanggal!!
                )
            )


        }
        jobAddData.join()
        GlobalScope.launch(Dispatchers.Main) {
            mPresenter.saveData(data, idUser!!, idKanCab!!, idName!!, tanggal!!)
        }
    }
}
