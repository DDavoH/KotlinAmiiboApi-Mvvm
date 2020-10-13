package com.davoh.kotlinamiiboapi.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.davoh.kotlinamiiboapi.R
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import kotlinx.android.synthetic.main.amiibo_row.view.*

class FavoriteAdapter(private val context: Context, private val amiibosList: List<AmiiboEntity>,
                  private val itemClickListener:OnAmiiboClickListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.amiibo_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(amiibosList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return amiibosList.size
    }

    //inner class evitar un leak de memory se cierra al cerrarse el mainadapter
    inner class MainViewHolder(itemView: View) : BaseViewHolder<AmiiboEntity>(itemView) {
        override fun bind(item: AmiiboEntity, position: Int) {
            Glide.with(context).load(item.image).fitCenter().into(itemView.imagen)
            itemView.txtName.text = item.name
            itemView.txtAmiiboSeries.text = item.amiiboSeries
            itemView.setOnClickListener{itemClickListener.onAmiiboClick(item)}
        }
    }

    interface OnAmiiboClickListener{
        fun onAmiiboClick(amiibo: AmiiboEntity)
    }

}