package com.melikearikan.rehberprojesi2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_kisi_detaylari_fragment.*
import java.io.ByteArrayOutputStream
import java.lang.Exception


class kisiDetaylari_fragment : Fragment() {
    var secilenGorsel : Uri? = null
    var secilenBitmap :Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kisi_detaylari_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        imageView2.setOnClickListener{
            gorselSec(it)
        }

        button.setOnClickListener {
            kaydet(it)
        }

        arguments?.let {
            var gelenBilgi = kisiDetaylari_fragmentArgs.fromBundle(it).bilgi
            if (gelenBilgi == "kisiekle"){
                kisiAdi_et.setText("")
                kisiNumara_et.setText("")
                kisiCinsiyet_et.setText("")
                kisiHatTuru_et.setText("")

                button.visibility = View.VISIBLE
                var gorselSecmeArkaPlani = BitmapFactory.decodeResource(context?.resources,R.drawable.arkaplan)
                imageView2.setImageBitmap(gorselSecmeArkaPlani)

            }else if(gelenBilgi == "kisigoster"){
                button.visibility = View.INVISIBLE
                val secilenId = kisiDetaylari_fragmentArgs.fromBundle(it).id
                context?.let {
                    try {
                        var db = it.openOrCreateDatabase("rehber", Context.MODE_PRIVATE,null)
                        var cursor = db.rawQuery("SELECT * FROM kisiler WHERE id =?", arrayOf(secilenId.toString()))
                        val kisiAdIndex = cursor.getColumnIndex("kisiad")
                        var kisiCinsiyetIndex = cursor.getColumnIndex("kisicinsiyet")
                        var kullaniciNoIndex = cursor.getColumnIndex("kisinumara")
                        var hatturuIndex = cursor.getColumnIndex("hatturu")
                        var gorsel = cursor.getColumnIndex("gorsel")

                        while (cursor.moveToNext()){
                            kisiAdi_et.setText(cursor.getString(kisiAdIndex))
                            kisiCinsiyet_et.setText(cursor.getString(kisiCinsiyetIndex))
                            kisiNumara_et.setText(cursor.getString(kullaniciNoIndex))
                            kisiHatTuru_et.setText(cursor.getString(hatturuIndex))
                            val byteDizisi = cursor.getBlob(gorsel)
                            val bitmapGorsel = BitmapFactory.decodeByteArray(byteDizisi,0,byteDizisi.size)
                            imageView2.setImageBitmap(bitmapGorsel)
                        }
                        cursor.close()
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
        }

    }

    fun gorselSec(view: View){
        activity?.let {
            if (ContextCompat.checkSelfPermission(it.applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
               requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }
            else{
                var galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            var galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent,2)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
           secilenGorsel = data.data

           try {
               context?.let {
                   if (secilenGorsel != null){
                      if (Build.VERSION.SDK_INT>=28){
                          var source = ImageDecoder.createSource(it.contentResolver,secilenGorsel!!)
                          secilenBitmap = ImageDecoder.decodeBitmap(source)
                          imageView2.setImageBitmap(secilenBitmap)
                      }else{
                          secilenBitmap = MediaStore.Images.Media.getBitmap(it.contentResolver,secilenGorsel)
                          imageView2.setImageBitmap(secilenBitmap)

                      }
                   }
               }

           }catch (e: Exception){
               e.printStackTrace()

           }

       }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun kaydet(view: View){
        var kisiAdi = kisiAdi_et.text.toString()
        var kisiNumara = kisiNumara_et.text.toString()
        var kisiCinsiyet = kisiCinsiyet_et.text.toString()
        var kisiHatTuru = kisiHatTuru_et.text.toString()

        if (secilenBitmap != null){
            val kucukBitmap = kucukBitmapOlustur(secilenBitmap!!,500)
            val outputStream = ByteArrayOutputStream()
            kucukBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            var byteDizisi = outputStream.toByteArray()

            try {
                context?.let {


                    var db = it.openOrCreateDatabase("rehber",Context.MODE_PRIVATE,null)
                    db.execSQL("CREATE TABLE IF NOT EXISTS kisiler(id INTEGER PRIMARY KEY,kisiad VARCHAR,kisinumara VARCHAR,kisicinsiyet VARCHAR,hatturu VARCHAR,gorsel BLOB)")
                   //db.execSQL("INSERT INTO kisiler(kisiad,kisinumara,kisicinsiyet,hatturu,gorsel) VALUES('meryem','05061656568','kadin','sabit','byteDizisi')") // veri tabanına yeni veri eklemek için aynı satırı kopyala yapıştır yapıyrum


                    val sqlString = "INSERT INTO kisiler(kisiad,kisinumara,kisicinsiyet,hatturu,gorsel) VALUES(?,?,?,?,?)"
                    val statement = db.compileStatement(sqlString)
                    statement.bindString(1,kisiAdi)
                    statement.bindString(2,kisiNumara)
                    statement.bindString(3,kisiCinsiyet)
                    statement.bindString(4,kisiHatTuru)
                    statement.bindBlob(5,byteDizisi)
                    statement.execute()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            val action = kisiDetaylari_fragmentDirections.actionKisiDetaylariFragmentToKisiListesiFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    fun kucukBitmapOlustur(kullanicininSectiğiBitmap :Bitmap, maxboyut: Int):Bitmap{
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