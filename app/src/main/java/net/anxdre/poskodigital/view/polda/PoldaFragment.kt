package net.anxdre.poskodigital.view.polda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import kotlinx.android.synthetic.main.fragment_polda.*
import kotlinx.android.synthetic.main.layout_polda_info.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.adapter.DataPoldaAdapter
import net.anxdre.poskodigital.data.api.apiPostBasicRequestAsync
import net.anxdre.poskodigital.data.api.model.polda.PoldaItem
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class PoldaFragment : Fragment() {
    var yearSelected: Int? = null
    var monthSelected: Int? = null
    var date: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val calendar: Calendar = Calendar.getInstance()
        yearSelected = calendar.get(Calendar.YEAR)
        monthSelected = calendar.get(Calendar.MONTH)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_polda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dialogFragment = MonthYearPickerDialogFragment
            .getInstance(monthSelected!!, yearSelected!!)

        btn_filter_polda.setOnClickListener {
            dialogFragment.show(parentFragmentManager, null)
        }

        dialogFragment.setOnDateSetListener { year, monthOfYear ->
            if (monthOfYear.toString().length < 2) {
                date = "$year-0${monthOfYear + 1}"
                tv_bulan_polda.text = date
            } else {
                date = "$year-${monthOfYear + 1}"
                tv_bulan_polda.text = date
            }
            GlobalScope.launch(Dispatchers.Main) { getData() }
        }
    }

    private suspend fun getData() {
        try {
            val a: PoldaItem = apiPostBasicRequestAsync(
                "${BuildConfig.BASE_URL}item_polda_get_by_date",
                PoldaItem::class.java,
                Pair("tanggal", date!!),
                Pair(" ", " ")
            ).await() as PoldaItem
            showData(a)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Data Tidak Ditemukan", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun showData(dataSource: PoldaItem) {
        btn_total_polda.visibility = View.VISIBLE
        btn_total_polda.setOnClickListener { showInfo(dataSource) }
        with(rv_polda_item) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DataPoldaAdapter(dataSource.x0) {
                //onCLick()
            }
        }
    }

    private fun showInfo(dataSource: PoldaItem) {
        val dialog = MaterialDialog(requireContext()).customView(
            R.layout.layout_polda_info,
            scrollable = true
        ).title(text = "Jumlah Kasus Polda Jawa Timur")
        var lp = 0
        var selra = 0
        var lidik = 0
        var p21 = 0
        var sp3 = 0
        var limpah = 0
        var sisalp = 0

        for (i in dataSource.x0.indices) {
            lp += (dataSource.x0[i].jumlahLp.toInt())
            selra += (dataSource.x0[i].totalSelra.toInt())
            lidik += (dataSource.x0[i].hentiLidik.toInt())
            p21 += (dataSource.x0[i].p21.toInt())
            sp3 += (dataSource.x0[i].sp3.toInt())
            limpah += (dataSource.x0[i].limpah.toInt())
            sisalp += (dataSource.x0[i].sisaLp.toInt())
        }

        val customView = dialog.getCustomView()
        customView.et_lp.setText(lp.toString())
        customView.et_selra.setText(selra.toString())
//        customView.et_lidik.setText(lidik.toString())
//        customView.et_p21.setText(p21.toString())
//        customView.et_sp3.setText(sp3.toString())
//        customView.et_limpah.setText(limpah.toString())
        customView.et_sisa_lp.setText(sisalp.toString())

        dialog.show()
    }
}
