package net.anxdre.poskodigital.view.pertanianinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.fragment_pertanian_input.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanianInput
import net.anxdre.poskodigital.data.api.remote.pertanian.PertanianResponse
import net.anxdre.poskodigital.utils.SpFactory

/**
 * A simple [Fragment] subclass.
 */
class PertanianInput : Fragment(), PertanianInputView {
    private val mApi by lazy { PertanianResponse() }
    private val mPresenter by lazy { PertanianInputPresenter(mApi, this) }
    private var dataSet: ItemPertanianInput? = null
    private var idData: String? = null
    private var idUser: String? = null
    private var idName: String? = null
    private var selectedIdItem: String? = null
    private var tanggal: String? = null

    private val idItem = listOf(
        "7",
        "6",
        "17",
        "5",
        "16",
        "3",
        "4",
        "18",
        "8"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idUser = SpFactory(requireContext()).getAuthId()
        idName = SpFactory(requireContext()).getAuthName()
        dataSet = arguments?.getParcelable("dataSet")
        tanggal = arguments?.getString("tanggal")
        idData = arguments?.getString("idData")
        selectedIdItem = arguments?.getString("selectedIdItem")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pertanian_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()

        sp_input_pertanian.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedIdItem = idItem[sp_input_pertanian.selectedItemPosition]
            }

        }

        btn_simpan_pertanian.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                prepareDataForInput(
                    selectedIdItem,
                    idData
                )
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showLoading() {
        pb_pertanian_input.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_pertanian_input.visibility = View.GONE
    }

    override fun showData(dataSet: ItemPertanianInput) {
        et_input_stok.setText(dataSet.sisaStok)
        et_input_konsumsi.setText(dataSet.konsumsi)
        et_input_produksi.setText(dataSet.produksi)
    }

    override fun backNav() {
        findNavController().navigateUp()
    }

    override fun showError(msg: String) {
        MaterialDialog(requireContext()).show {
            title(text = "Error")
            message(text = msg)
        }
        pb_pertanian_input.visibility = View.GONE
        btn_simpan_pertanian.visibility = View.VISIBLE
    }

    private fun initialize() {
        val item = listOf(
            "Bawang Putih",
            "Cabai Merah",
            "Cabai Merah Keriting",
            "Cabai Rawit Merah",
            "Beras",
            "Jagung",
            "Kacang Kedelai",
            "Kacang Tanah",
            "Bawang Merah"
        )

        sp_input_pertanian.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            item
        )

        if (selectedIdItem == null) {
            selectedIdItem = idItem[sp_input_pertanian.selectedItemPosition]
        } else {
            for (i in idItem.indices) {
                if (selectedIdItem == idItem[i]) {
                    selectedIdItem = idItem[i]
                    sp_input_pertanian.setSelection(i)
                    sp_input_pertanian.isEnabled = false
                    sp_input_pertanian.isClickable = false
                }
            }
            showData(dataSet!!)
        }

        tv_date_input_pertanian.text = tanggal
    }

    private suspend fun prepareDataForInput(idItem: String?, idDataInput: String?) {
        val data = tanggal?.let {
            ItemPertanianInput(
                dataSet?.id,
                idData,
                selectedIdItem!!,
                et_input_stok.text.toString(),
                tanggal!!,
                et_input_produksi.text.toString(),
                et_input_konsumsi.text.toString()
            )
        }
        btn_simpan_pertanian.visibility = View.INVISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            if (data != null) {
                mPresenter.saveData(
                    data,
                    idUser!!,
                    idName!!,
                    tanggal!!
                )
            }
        }
    }
}
