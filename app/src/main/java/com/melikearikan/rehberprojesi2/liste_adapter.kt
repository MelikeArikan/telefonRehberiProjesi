package com.melikearikan.rehberprojesi2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.melikearikan.rehberprojesi2.liste_adapter.kisiHolder
import kotlinx.android.synthetic.main.fragment_kisi_listesi_fragment.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class liste_adapter(val kisiListe : ArrayList<String>, var IdList : ArrayList<Int>):RecyclerView.Adapter<kisiHolder>(){
    class kisiHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): kisiHolder {
        var inflater = LayoutInflater.from(parent.context)
        val view  = inflater.inflate(R.layout.recycler_row,parent,false)
        return kisiHolder(view)
    }

    override fun onBindViewHolder(holder: kisiHolder, position: Int) {
        holder.itemView.recyclerText.text = kisiListe.get(position)
        holder.itemView.setOnClickListener {
            var action = kisiListesi_fragmentDirections.actionKisiListesiFragmentToKisiDetaylariFragment("kisigoster",IdList.get(position))
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return kisiListe.size

    }

}