package net.anxdre.poskodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_polda.view.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.polda.PoldaItem

class DataPoldaAdapter(
    private val dataSource: List<PoldaItem.X0>,
    private val listener: (PoldaItem.X0) -> Unit
) : RecyclerView.Adapter<PoldaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoldaViewHolder =
        PoldaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_polda, parent, false)
        )

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: PoldaViewHolder, position: Int) =
        holder.bindItem(dataSource[position], listener)
}

class PoldaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: PoldaItem.X0, listener: (PoldaItem.X0) -> Unit) {
        itemView.tv_polda_name.text = items.namaSatuan
        itemView.tv_total_lp.text = "Jumlah LP : ${items.jumlahLp}"
//        itemView.et_total_lidik.setText(items.hentiLidik)
//        itemView.et_total_limpah.setText(items.limpah)
//        itemView.et_total_p21.setText(items.p21)
        itemView.et_total_selra.setText(items.totalSelra)
        itemView.et_total_sisa.setText(items.sisaLp)
//        itemView.et_total_sp3.setText(items.sp3)
//        itemView.setOnClickListener {
//            listener(items)
//        }
    }
}
