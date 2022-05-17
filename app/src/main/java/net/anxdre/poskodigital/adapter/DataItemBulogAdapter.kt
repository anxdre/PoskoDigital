package net.anxdre.poskodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_perindag.view.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.bulog.ItemBulog

class DataItemBulogAdapter(
    private val dataSource: List<ItemBulog.X0>,
    private val listener: (ItemBulog.X0) -> Unit
) : RecyclerView.Adapter<DataItemBulogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemBulogViewHolder =
        DataItemBulogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_perindag, parent, false)
        )

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: DataItemBulogViewHolder, position: Int) =
        holder.bindItem(dataSource[position], listener)
}


class DataItemBulogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: ItemBulog.X0, listener: (ItemBulog.X0) -> Unit) {
        itemView.tv_komuditas.text = items.namaKomoditas
        itemView.tv_desc.text =
            "Stok Komoditas ${items.namaKomoditas} Pada ${items.namaKancab}"
        if (itemView.tv_komuditas.text == "Minyak Goreng") {
            itemView.tv_harga.text = "${items.stok} Liter"
        } else {
            itemView.tv_harga.text = "${items.stok} Ton"
        }
        itemView.setOnClickListener {
            listener(items)
        }
    }
}