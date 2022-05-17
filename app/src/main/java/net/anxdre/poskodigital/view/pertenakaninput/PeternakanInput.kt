package net.anxdre.poskodigital.view.pertenakaninput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.fragment_peternakan_input.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItem
import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItemInput
import net.anxdre.poskodigital.data.api.remote.peternakan.PeternakanResponse
import net.anxdre.poskodigital.utils.SpFactory

/**
 * A simple [Fragment] subclass.
 */
class PeternakanInput : Fragment(), PeternakanInputView {
    private val mApi by lazy { PeternakanResponse() }
    private val mPresenter by lazy { PeternakanInputPresenter(mApi, this) }
    private var dataSet: PeternakanItem.X0? = null
    private var idUser: String? = null
    private var idName: String? = null
    private var tanggal: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataSet = arguments?.getParcelable("dataSet")
        idUser = SpFactory(requireContext()).getAuthId()
        idName = SpFactory(requireContext()).getAuthName()
        tanggal = arguments?.getString("tanggal")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_peternakan_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataSet?.let { showData(it) }
        tv_tanggal_peternakan.text = tanggal
        btn_save_data.setOnClickListener { prepareDataForInput() }
    }

    override fun showLoading() {
        btn_save_data.visibility = View.GONE
        pb_peternakan_input.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        btn_save_data.visibility = View.VISIBLE
        pb_peternakan_input.visibility = View.GONE
    }

    override fun showData(dataSet: PeternakanItem.X0) {
        et_stok_sapi_input.setText(dataSet.ketersediaanSapi)
        et_total_requirement_sapi_input.setText(dataSet.kebutuhanSapi)

        et_stok_ayam_input.setText(dataSet.ketersediaanAyam)
        et_total_requirement_ayam_input.setText(dataSet.kebutuhanAyam)

        et_stok_telur_input.setText(dataSet.ketersediaanTelur)
        et_total_requirement_telur_input.setText(dataSet.kebutuhanTelur)
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

    private fun prepareDataForInput() {
        val dataInput: PeternakanItemInput
        dataInput =
            PeternakanItemInput(
                dataSet?.id,
                idUser!!,
                idName!!,
                et_stok_sapi_input.text.toString(),
                et_stok_ayam_input.text.toString(),
                et_stok_telur_input.text.toString(),
                et_total_requirement_sapi_input.text.toString(),
                et_total_requirement_ayam_input.text.toString(),
                et_total_requirement_telur_input.text.toString(),
                tanggal!!
            )
        GlobalScope.launch(Dispatchers.Main) {
            mPresenter.saveData(dataInput, tanggal!!)
        }
    }
}
