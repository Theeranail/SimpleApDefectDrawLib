package com.example.simpleapdefectdrawlib

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.apdefectdrawlibrary.APDefectDraw
import com.example.apdefectdrawlibrary.DefectDrawBuilder
import com.example.apdefectdrawlibrary.OnDefectDrawListener
import com.example.simpleapdefectdrawlib.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnDefectDrawListener {
    private lateinit var apDefectDraw: APDefectDraw
    private var lastOrientation = 0
    private lateinit var vBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.vBinding.root)

        if (savedInstanceState == null) {
            lastOrientation = resources.configuration.orientation
            Log.e("MainActivity", "lastOrientation -> ${lastOrientation}")
        }

        //how to use Option 1
//        defectDraw.imagePath =
//            "https://www.banidea.com/wp-content/uploads/2012/04/lower-floor-plan.jpg"
//        defectDraw.onDefectDrawListener = this
//        defectDraw.initViewImage();

        //how to use Option 2              .imagePath("https://cdn-cms.pgimgs.com/static/2017/07/8-03-4-1024x989-1024x989.jpg")
        apDefectDraw = DefectDrawBuilder.instances()
            .defectDrawListener(this)
            .isShowButtonSave(false)
            .strokeWidth(5.0f)
            .setStrokeColor(Color.RED)
            .isShowTools(true)
            .setBackgroundColor(android.R.color.transparent)
            .build(vBinding.defectDraw)

        vBinding.defectDrawText.onDefectDrawListener = this

        vBinding.btnSaveS.setOnClickListener {
            apDefectDraw.exportBitmap()
//            defectDrawText.exportBitmap()
        }

        vBinding.btnResetS.setOnClickListener {
            apDefectDraw.reset()
        }

    }

    override fun onStart() {
        Log.e("MainActivity", "onStart lastOrientation -> $lastOrientation")
        super.onStart()

    }

    override fun onSaveDraw(image: Bitmap?) {
        if (image != null) {
            Toast.makeText(this, "export image to Bitmap success", Toast.LENGTH_SHORT).show()
            apDefectDraw.reset()
            Glide
                .with(this)
                .load(image)
                .into(vBinding.imgView)
        } else {
            Toast.makeText(this, "export image to Bitmap Invalid", Toast.LENGTH_SHORT).show()
        }
    }
}