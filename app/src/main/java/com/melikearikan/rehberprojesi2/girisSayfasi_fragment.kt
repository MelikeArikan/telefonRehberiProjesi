package com.melikearikan.rehberprojesi2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_giris_sayfasi_fragment.*


class girisSayfasi_fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_giris_sayfasi_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button2.setOnClickListener {
            kisiekle(it)
        }
        button3.setOnClickListener {
            rehber(it)
        }


    }

    fun kisiekle(view: View){
        var action = girisSayfasi_fragmentDirections.actionGirisSayfasiFragmentToKisiDetaylariFragment("kisiekle",0)
        Navigation.findNavController(view).navigate(action)


    }
    fun rehber(view: View){
        var action = girisSayfasi_fragmentDirections.actionGirisSayfasiFragmentToKisiListesiFragment()
        Navigation.findNavController(view).navigate(action)


    }



}