package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.ItemRvBinding

class RecyAdapter(var list: ArrayList<recymodel>,val rvAction: RvAction):RecyclerView.Adapter<RecyAdapter.VH>() {
    inner class VH(val itemRv:ItemRvBinding):ViewHolder(itemRv.root){
        fun onBind(recymodel: recymodel){
            itemRv.name.text = recymodel.name
            itemRv.number.text = recymodel.number
            itemRv.btnDel.setOnClickListener{
                rvAction.rvdeleteClick(recymodel)
            }
            itemRv.btnEdit.setOnClickListener{
                rvAction.rvEditClick(recymodel)
            }
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }
    interface RvAction{
        fun rvdeleteClick(recymodel: recymodel)
        fun rvEditClick(recymodel: recymodel)
    }
}