package net.anxdre.poskodigital.view.kegiatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_kegiatan_webview.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.utils.SpFactory

/**
 * A simple [Fragment] subclass.
 */
class KegiatanWebview : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kegiatan_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        wv_kegiatan.loadUrl("http://poskodigitalsatgaspangan.net/main/dokumentasi")
        btn_input_kegiatan.setOnClickListener { findNavController().navigate(R.id.action_kegiatanWebview_to_nav_kegiatan) }
    }

    private fun initialize() {
        if (SpFactory(requireContext()).getAuthRole() == "false") {
            btn_input_kegiatan.visibility = View.GONE
        }
    }
}
