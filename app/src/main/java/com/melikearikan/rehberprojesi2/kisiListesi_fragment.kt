package com.melikearikan.rehberprojesi2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_kisi_listesi_fragment.*
import java.io.ByteArrayOutputStream
import java.lang.Exception


class kisiListesi_fragment : Fragment() {

    var kisiAdArrayList = ArrayList<String>()
    var kisiIDArrayList = ArrayList<Int>()
    private lateinit var listeAdapter : liste_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kisi_listesi_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeAdapter = liste_adapter(kisiAdArrayList,kisiIDArrayList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = listeAdapter
        sqlno()
        sqlVeriAlma()
    }

    fun sqlno(){
        try {
            activity?.let {
                var db = it.openOrCreateDatabase("rehber", Context.MODE_PRIVATE,null)
                db.execSQL("CREATE TABLE IF NOT EXISTS kisiler(id INTEGER PRIMARY KEY,kisiad VARCHAR,kisinumara VARCHAR,kisicinsiyet VARCHAR,hatturu VARCHAR,gorsel BLOB)")

                var cursor = db.rawQuery("SELECT * FROM kisiler",null)


                if (cursor.moveToFirst()) {
                    println("null değil")
                } else {
                    println("null")
                    var bitmapGorsel1 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap1)
                    var bitmapGorseli2 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap2)
                    var bitmapGorseli3 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap3)
                    var bitmapGorseli4 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap4)
                    var bitmapGorseli5 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap5)
                    var bitmapGorseli6 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap6)
                    var bitmapGorseli7 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap7)
                    var bitmapGorseli8 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap8)
                    var bitmapGorseli9 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap9)
                    var bitmapGorseli10 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap10)
                    var bitmapGorseli11 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap11)
                    var bitmapGorseli12 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap12)
                    var bitmapGorseli13 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap13)
                    var bitmapGorseli14 = BitmapFactory.decodeResource(context?.resources,R.drawable.btmap14)
                    var bitmapGorseli15 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap15)
                    var bitmapGorseli16 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap16)
                    var bitmapGorseli17 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap17)
                    var bitmapGorseli18 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap18)
                    var bitmapGorseli19 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap19)
                    var bitmapGorseli20 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap20)
                    var bitmapGorseli21 = BitmapFactory.decodeResource(context?.resources,R.drawable.bitmap21)

                    var BitmapDizisi = arrayOf<Bitmap>(bitmapGorsel1,bitmapGorseli2,bitmapGorseli3,bitmapGorseli4,bitmapGorseli5,bitmapGorseli6,bitmapGorseli7,bitmapGorseli8,bitmapGorseli9,bitmapGorseli10,bitmapGorseli11,bitmapGorseli12,bitmapGorseli13,bitmapGorseli14,bitmapGorseli15,bitmapGorseli16,bitmapGorseli17,bitmapGorseli18,bitmapGorseli19,bitmapGorseli20,bitmapGorseli21)
                    var isimDizisi = arrayOf("yangın ihbar","hızır acil","Bilinmeyen Numara","Posta Kodu Danışma","Telefon Arıza","BİMER","Belediye Beyaz Masa","Polis İmdat","Jandarma İmdat","P.T.T Danışma","Elektrik Arıza","Alo Posta","Orman Yangın","İş ve İşçi Bulma Kurumu","Sağlık Danışma","Su Arıza","Gaz Arıza","Vergi Danışma","Devlet Hastanesi","Tıp Fakültesi","Alo Trafik" )
                    var noDizisi = arrayOf(" 110","112","11818","119","121","150","153","155","156","161","186","169","177","180","182","185","187","189","524 37 40","541 41 07","154")
                    var cinsiyetDizisi = arrayOf("---","---","---","---","---","---","---","---","---","---","---","---","---","---","---","---","---","---","---","---","---")
                    var hatturuDizisi = arrayOf("sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit","sabit")
                    var j = 0
                    for (i in BitmapDizisi){

                        if (i != null) {
                        val kucukBitmap = kucukBitmapOlustur(i!!, 500)
                        val outputStream = ByteArrayOutputStream()
                        kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                        var byteDizisi = outputStream.toByteArray()
                            val sqlString = "INSERT INTO kisiler(kisiad,kisinumara,kisicinsiyet,hatturu,gorsel) VALUES(?,?,?,?,?)"
                            val statement = db.compileStatement(sqlString)
                            statement.bindString(1,isimDizisi[j])
                            statement.bindString(2,noDizisi.get(j))
                            statement.bindString(3,cinsiyetDizisi.get(j))
                            statement.bindString(4,hatturuDizisi.get(j))
                            statement.bindBlob(5,byteDizisi)
                            statement.execute()
                    }
                        j++


                    }


                }
                cursor.close()
            }



        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    fun sqlVeriAlma(){
        try {
            activity?.let {

                var db = it.openOrCreateDatabase("rehber", Context.MODE_PRIVATE,null)
                var cursor = db.rawQuery("SELECT * FROM kisiler",null)
                val kisiAdIndex = cursor.getColumnIndex("kisiad")
                var kisiIDIndex = cursor.getColumnIndex("id")

                kisiAdArrayList.clear()
                kisiIDArrayList.clear()

                while (cursor.moveToNext()){
                    kisiAdArrayList.add(cursor.getString(kisiAdIndex))
                    kisiIDArrayList.add(cursor.getInt(kisiIDIndex))
                }
                 //kullanıcılar yeni yemek de eklicek haliyle veriler değişicek bu kod yeni bir veri geldiğini listeye söylicek ve recyclerView güncellenicek
                cursor.close()
            }
        }catch (e:Exception){
        }
    }
    fun kucukBitmapOlustur(kullanicininSectiğiBitmap : Bitmap, maxboyut: Int): Bitmap {
        var width = kullanicininSectiğiBitmap.width
        var height = kullanicininSectiğiBitmap.height
        val bitmapOrani :Double = width.toDouble() / height.toDouble()
        if (bitmapOrani > 1){
            width = maxboyut
            val kisaltilmisHeight = width /bitmapOrani
            height = kisaltilmisHeight.toInt()
        }else{
            height = maxboyut
            var kisaltilmisWidth = height * bitmapOrani
            width = kisaltilmisWidth.toInt()
        }
        return Bitmap.createScaledBitmap(kullanicininSectiğiBitmap,width,height,true)
    }
}