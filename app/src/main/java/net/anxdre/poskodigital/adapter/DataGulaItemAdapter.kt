package net.anxdre.poskodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_edit_perkebunan.view.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.perkebunan.PerkebunanGula

class DataGulaItemAdapter(
    private val dataSource: List<PerkebunanGula.X0>,
    private val listener: (PerkebunanGula.X0) -> Unit
) : RecyclerView.Adapter<DataGulaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataGulaViewHolder =
        DataGulaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_edit_perkebunan, parent, false)
        )

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: DataGulaViewHolder, position: Int) =
        holder.bindItem(dataSource[position], listener)
}

class DataGulaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: PerkebunanGula.X0, listener: (PerkebunanGula.X0) -> Unit) {
        itemView.tv_storange_name.text = items.namaPabrik
        itemView.tv_total_storange.text = "${items.total} Ton"
        itemView.et_total_factory.setText(items.jumlahPabrik)
        itemView.et_total_farmer.setText(items.jumlahPetani)
        itemView.et_total_trader.setText(items.jumlahPedagang)
        itemView.setOnClickListener {
            listener(items)
        }
    }
}