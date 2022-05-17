package net.anxdre.poskodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_perindag.view.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindagItem

class DataDetailPerindagAdapter(
    private val dataSource: List<DataDetailPerindagItem>,
    private val listener: (DataDetailPerindagItem) -> Unit
) : RecyclerView.Adapter<PerindagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerindagViewHolder =
        PerindagViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_perindag, parent, false)
        )

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: PerindagViewHolder, position: Int) =
        holder.bindItem(dataSource[position], listener)
}

class PerindagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: DataDetailPerindagItem, listener: (DataDetailPerindagItem) -> Unit) {
        itemView.tv_desc.text = "Harga ${items.namaKomoditas} di ${items.namaKota}"
        itemView.tv_harga.text = "Rp. ${items.harga}"
        itemView.tv_komuditas.text = items.namaKomoditas
        itemView.setOnClickListener {
            listener(items)
        }
    }
}
