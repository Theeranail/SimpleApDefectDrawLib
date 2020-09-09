package com.example.simpleapdefectdrawlib

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.apdefectdrawlibrary.APDefectDraw
import com.example.apdefectdrawlibrary.DefectDrawBuilder
import com.example.apdefectdrawlibrary.OnDefectDrawListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnDefectDrawListener {
    lateinit var apDefectDraw: APDefectDraw
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //how to use Option 1
//        defectDraw.imagePath =
//            "https://www.banidea.com/wp-content/uploads/2012/04/lower-floor-plan.jpg"
//        defectDraw.onDefectDrawListener = this
//        defectDraw.initViewImage();

        //how to use Option 2
        apDefectDraw = DefectDrawBuilder.instances()
            .imagePath("https://www.banidea.com/wp-content/uploads/2012/04/lower-floor-plan.jpg")
            .defectDrawListener(this)
            .isShowButtonSave(false)
            .build(defectDraw)

    }

    override fun onSaveDraw(image: Bitmap?) {
        if (image != null) {
            Toast.makeText(this, "export image to Bitmap success", Toast.LENGTH_SHORT).show()
            apDefectDraw.reset()
            defectDraw.visibility = View.GONE
            imgView.visibility = View.VISIBLE
            Glide
                .with(this)
                .load(image)
                .into(imgView);
        } else {
            Toast.makeText(this, "export image to Bitmap Invalid", Toast.LENGTH_SHORT).show()
        }
    }
}