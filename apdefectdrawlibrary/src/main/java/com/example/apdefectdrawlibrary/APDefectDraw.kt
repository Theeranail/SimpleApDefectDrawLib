package com.example.apdefectdrawlibrary

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.drawToBitmap
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_draw.view.*

class APDefectDraw : FrameLayout, View.OnClickListener {

    private val colors = ColorConfig.colorList
    private var colorSelected: Int = Color.BLACK

    var imagePath: String = ""
    var strokeWidth: Float = 2.0f
    var onDefectDrawListener: OnDefectDrawListener? = null

    constructor(@NonNull ctx: Context) : super(ctx) {
        this.setUpInit()
    }

    constructor(@NonNull ctx: Context, @Nullable attrs: AttributeSet) : super(ctx, attrs) {
        this.setUpInit()
    }

    constructor(@NonNull ctx: Context, @Nullable attrs: AttributeSet, defStyleAttr: Int) : super(
        ctx,
        attrs,
        defStyleAttr
    ) {
        this.setUpInit()
    }

    private fun setUpInit() {
        inflate(context, R.layout.view_draw, this)
        drawView.setStrokeWidth(strokeWidth)
        setBackgroundColorButtonColor(colorSelected)
        btnUndo.setOnClickListener(this)
        btnReDo.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnColor.setOnClickListener(this)
    }

    fun initViewImage(): APDefectDraw {
        if (this.imagePath.isNotEmpty()) {
            Glide
                .with(context)
                .load(this.imagePath)
                .into(imgViewBackground)
        } else {
            drawView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_white))
        }
        return this
    }

    fun reset() {
        drawView.clearCanvas()
    }

    private fun unDo() {
        drawView.undo()
    }

    private fun reDo() {
        drawView.redo()
    }

    private fun save() {
        val exportBM = containerDraw.drawToBitmap(Bitmap.Config.ARGB_8888)
        onDefectDrawListener?.onSaveDraw(exportBM)
    }

    private fun setNewColor(color: Int) {
        drawView.setColor(color)
    }

    private fun setBackgroundColorButtonColor(color: Int) {
        var buttonDrawable = btnColor.background
        buttonDrawable = DrawableCompat.wrap(buttonDrawable)
        DrawableCompat.setTint(buttonDrawable, color)
        btnColor.background = buttonDrawable
    }

    private fun selectColor() {
        MaterialDialog(context).show {
            title(R.string.title_dialog_color)
            colorChooser(
                colors = colors,
                allowCustomArgb = true,
                showAlphaSelector = true,
                initialSelection = colorSelected
            ) { _, color ->
                colorSelected = color
                setNewColor(color)
                setBackgroundColorButtonColor(color)
            }
            positiveButton(R.string.text_select_positive_button_dialog_color)
            negativeButton(R.string.text_cancel_positive_button_dialog_color)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnUndo -> this.unDo()
            R.id.btnReDo -> this.reDo()
            R.id.btnSave -> this.save()
            R.id.btnColor -> this.selectColor()
        }
    }

}