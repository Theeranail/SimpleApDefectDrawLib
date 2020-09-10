package com.example.apdefectdrawlibrary

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_draw.view.*

class APDefectDraw : FrameLayout, View.OnClickListener {

    private val colors = ColorConfig.colorList

    var strokeColor: Int = Color.BLACK
    var imagePath: String = ""
    var strokeWidth: Float = 2.0f
    var onDefectDrawListener: OnDefectDrawListener? = null
    var backgroundColorId: Int = R.color.color_white

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
        setStrokeWith(strokeWidth)
        setBackgroundColorButtonColor(strokeColor)
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
            setBackgroundColorDraw(backgroundColorId)
        }
        setBackgroundColorButtonColor(strokeColor)
        setStrokeWith(strokeWidth)
        return this
    }

    fun reset() {
        drawView.clearCanvas()
    }

    fun exportBitmap() {
        this.save()
    }

    fun isShowButtonSave(isShow: Boolean) {
        if (isShow)
            containerBtnSave.visibility = View.VISIBLE
        else
            containerBtnSave.visibility = View.GONE
    }

    fun isShowTools(isShow: Boolean) {
        if (isShow)
            containerTools.visibility = View.VISIBLE
        else
            containerTools.visibility = View.GONE
    }

    fun setStrokeWith(strokeWith: Float) {
        this.strokeWidth = strokeWith
        drawView.setStrokeWidth(strokeWith)
    }

    fun setColorStroke(color: Int) {
        strokeColor = color
        setBackgroundColorButtonColor(color)
    }

    fun setBackgroundColorDraw(colorId: Int) {
        if (imagePath.isEmpty()) {
            backgroundColorId = colorId
            drawView.setBackgroundColor(ContextCompat.getColor(context, colorId))
        }
    }

    fun undo() {
        drawView.undo()
    }

    fun redo() {
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
        val drawableBackground = resources.getDrawable(R.drawable.btn_radius, null)
        drawableBackground.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        btnColor.background = drawableBackground
    }

    private fun selectColor() {
        MaterialDialog(context).show {
            title(R.string.title_dialog_color)
            colorChooser(
                colors = colors,
                allowCustomArgb = true,
                showAlphaSelector = true,
                initialSelection = strokeColor
            ) { _, color ->
                strokeColor = color
                setNewColor(color)
                setBackgroundColorButtonColor(color)
            }
            positiveButton(R.string.text_select_positive_button_dialog_color)
            negativeButton(R.string.text_cancel_positive_button_dialog_color)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnUndo -> this.undo()
            R.id.btnReDo -> this.redo()
            R.id.btnSave -> this.save()
            R.id.btnColor -> this.selectColor()
        }
    }

}