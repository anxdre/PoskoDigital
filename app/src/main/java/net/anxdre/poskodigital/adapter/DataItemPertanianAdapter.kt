package net.anxdre.poskodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_pertanian.view.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanian

class DataItemPertanianAdapter(
    private val dataSource: List<ItemPertanian.X0>,
    private val listener: (ItemPertanian.X0) -> Unit
) : RecyclerView.Adapter<DataItemPertanianViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemPertanianViewHolder =
        DataItemPertanianViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_pertanian, parent, false)
        )

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: DataItemPertanianViewHolder, position: Int) =
        holder.bindItem(dataSource[position], listener)
}


class DataItemPertanianViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: ItemPertanian.X0, listener: (ItemPertanian.X0) -> Unit) {
        itemView.tv_komoditas_name.text = items.namaKomoditas
        itemView.et_total_stok.setText(items.sisaStok)
        itemView.et_total_consumption.setText(items.konsumsi)
        itemView.et_total_production.setText(items.produksi)
        itemView.tv_surplus.text = "Surplus / Devisit : ${items.defisit}"
        itemView.setOnClickListener {
            listener(items)
        }
    }
}