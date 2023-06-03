package net.anxdre.poskodigital.view.pejabat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_pejabat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.utils.fetchImage

/**
 * A simple [Fragment] subclass.
 */
class PejabatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pejabat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = Firebase.database
        val myRef = database.getReference("Pejabat")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val bulog = snapshot.child("Bulog")
                val disperindag = snapshot.child("Disperindag")
                val ditreskrimsus = snapshot.child("Ditreskrimsus")
                val perkebunan = snapshot.child("Perkebunan")
                val pertanian = snapshot.child("Pertanian")
                val peternakan = snapshot.child("Peternakan")

                //dirreskrimsus
                tv_ketua_ditreskrimsus.text = ditreskrimsus.child("nama").value.toString()
                fetchImage(ditreskrimsus.child("imgUrl").value.toString(), iv_ketua_ditreskrimsus)

                //bulog
                tv_pemimpin_bulog.text = bulog.child("nama").value.toString()
                fetchImage(bulog.child("imgUrl").value.toString(), iv_pemimpin_bulog)

                //disperindag
                tv_kepala_disperindag.text = ditreskrimsus.child("nama").value.toString()
                fetchImage(disperindag.child("imgUrl").value.toString(), iv_kepala_disperindag)

                //perkebunan
                tv_kepala_perkebunan.text = perkebunan.child("nama").value.toString()
                fetchImage(perkebunan.child("imgUrl").value.toString(), iv_kepala_perkebunan)

                //pertanian
                tv_kepala_pertanian.text = pertanian.child("nama").value.toString()
                fetchImage(pertanian.child("imgUrl").value.toString(), iv_kepala_pertanian)

                //peternakan
                tv_kepala_peternakan.text = peternakan.child("nama").value.toString()
                fetchImage(peternakan.child("imgUrl").value.toString(), iv_kepala_peternakan)

                animate()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun animate() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                ll_ditreskrimsus.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.zoom_out
                    )
                )

                ll_anggota_row1.visibility = View.VISIBLE
                ll_anggota_row1.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide
                    )
                )
                delay(500L)

                ll_anggota_row2.visibility = View.VISIBLE
                ll_anggota_row2.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide
                    )
                )
                delay(500L)

                ll_anggota_row3.visibility = View.VISIBLE
                ll_anggota_row3.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide
                    )
                )

            } catch (e: Exception) {

            }
        }
    }

}
