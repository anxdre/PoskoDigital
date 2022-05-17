package net.anxdre.poskodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_berita.view.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.kegiatan.KegiatanItem
import net.anxdre.poskodigital.utils.getImage

class DataKegiatanAdapter(
    private val dataSource: List<KegiatanItem.X0>,
    private val listener: (KegiatanItem.X0) -> Unit
) : RecyclerView.Adapter<KegiatanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KegiatanViewHolder =
        KegiatanViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_berita, parent, false)
        )

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: KegiatanViewHolder, position: Int) =
        holder.bindItem(dataSource[position], listener)
}

class KegiatanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: KegiatanItem.X0, listener: (KegiatanItem.X0) -> Unit) {
        itemView.tv_desc_berita.text = items.judul
        itemView.tv_harga_berita.text = items.deskripsi
        getImage(items.gambar, itemView.iv_komuditas_berita)
        itemView.setOnClickListener {
            listener(items)
        }
    }
}
