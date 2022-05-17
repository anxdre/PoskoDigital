package net.anxdre.poskodigital.view.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.datetime.datePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_filter.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.user.Komuditas
import net.anxdre.poskodigital.utils.OfflineJson
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class FilterFragment : Fragment() {
    private var gson = Gson()

    @SuppressLint("SimpleDateFormat")
    private val df = SimpleDateFormat("yyyy-MM-dd")
    private var tanggal1: String? = "Pilih Tanggal"
    private var tanggal2: String? = "Pilih Tanggal"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tanggal1 = df.format(Calendar.getInstance().time)
        tanggal2 = df.format(Calendar.getInstance().time)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeData()

        dp_perindag.setOnClickListener {
            MaterialDialog(requireContext()).show {
                datePicker { dialog, date ->
                    tanggal1 = df.format(date.time)
                }
            }.onDismiss {
                dp_perindag.text = tanggal1
            }
        }

        dp_perindag2.setOnClickListener {
            MaterialDialog(requireContext()).show {
                datePicker { dialog, date ->
                    tanggal2 = df.format(date.time)
                }
            }.onDismiss {
                dp_perindag2.text = tanggal2
            }
        }

        btn_save_filter.setOnClickListener {
            val a = bundleOf(
                Pair("id_komuditas", (sp_komuditas.selectedItemPosition + 1).toString()),
                Pair("namKomuditas", sp_komuditas.selectedItem.toString()),
                Pair("namSort", sp_sort.selectedItem.toString()),
                Pair("sort", sp_sort.selectedItemPosition),
                Pair(
                    "id_tanggal", dp_perindag.text
                ),
                Pair(
                    "id_tanggal2", dp_perindag2.text
                )
            )
//            findNavController().navigate(
//                R.id.action_filterFragment_to_nav_disperindag, a
//            )
        }
    }

    private fun initializeData() {
        val listKomuditasJson = gson.fromJson(OfflineJson.komuditas, Komuditas::class.java)
        val listKomuditas = arrayListOf<String>()
        val listSort = arrayListOf("Tertinggi", "Terendah")

        for (i in listKomuditasJson.indices) {
            listKomuditas.add(i, listKomuditasJson[i].nama!!)
        }
        sp_komuditas.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            listKomuditas
        )

        sp_sort.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            listSort
        )

        dp_perindag.text = tanggal1
        dp_perindag2.text = tanggal1
    }

}
