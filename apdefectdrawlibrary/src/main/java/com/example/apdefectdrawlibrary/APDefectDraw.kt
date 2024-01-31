package com.example.apdefectdrawlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.bumptech.glide.Glide
import com.example.apdefectdrawlibrary.databinding.ViewDrawBinding

class APDefectDraw : FrameLayout, View.OnClickListener {

    private lateinit var contentView: ViewDrawBinding

    private val colors = ColorConfig.colorList

    var strokeColor: Int = Color.BLACK
    var imagePath: String = ""
    var strokeWidth: Float = 2.0f
    var onDefectDrawListener: OnDefectDrawListener? = null
    var backgroundColorId: Int = R.color.color_white

    constructor(ctx: Context) : super(ctx) {
        this.setUpInit()
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        this.setUpInit()
    }

    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        ctx,
        attrs,
        defStyleAttr
    ) {
        this.setUpInit()
    }

    private fun setUpInit() {
        contentView = ViewDrawBinding.inflate(LayoutInflater.from(context))
        addView(contentView.root)
        setStrokeWith(strokeWidth)
        setBackgroundColorButtonColor(strokeColor)
        contentView.btnUndo.setOnClickListener(this)
        contentView.btnReDo.setOnClickListener(this)
        contentView.btnSave.setOnClickListener(this)
        contentView.btnColor.setOnClickListener(this)
    }

    fun initViewImage(): APDefectDraw {
        if (this.imagePath.isNotEmpty()) {
            Glide
                .with(context)
                .load(this.imagePath)
                .into(contentView.imgViewBackground)
        } else {
            setBackgroundColorDraw(backgroundColorId)
        }
        setBackgroundColorButtonColor(strokeColor)
        setStrokeWith(strokeWidth)
        return this
    }

    fun reset() {
        contentView.drawView.clearCanvas()
    }

    fun exportBitmap() {
        this.save()
    }

    fun isShowButtonSave(isShow: Boolean) {
        if (isShow)
            contentView.containerBtnSave.visibility = View.VISIBLE
        else
            contentView.containerBtnSave.visibility = View.GONE
    }

    fun isShowTools(isShow: Boolean) {
        if (isShow)
            contentView.containerTools.visibility = View.VISIBLE
        else
            contentView.containerTools.visibility = View.GONE
    }

    fun setStrokeWith(strokeWith: Float) {
        this.strokeWidth = strokeWith
        contentView.drawView.setStrokeWidth(strokeWith)
    }

    fun setColorStroke(color: Int) {
        strokeColor = color
        setNewColor(strokeColor)
        setBackgroundColorButtonColor(color)
    }

    fun setBackgroundColorDraw(colorId: Int) {
        if (imagePath.isEmpty()) {
            backgroundColorId = colorId
            contentView.drawView.setBackgroundColor(ContextCompat.getColor(context, colorId))
        }
    }

    fun undo() {
        contentView.drawView.undo()
    }

    fun redo() {
        contentView.drawView.redo()
    }

    fun isValidDraw(): Boolean = contentView.drawView.mPaths.isNotEmpty()

    private fun save() {
        val bitmap: Bitmap
        val nBitmap = Bitmap.createBitmap(
            contentView.containerDraw.measuredWidth,
            contentView.containerDraw.measuredHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(nBitmap)

        contentView.containerDraw.layout(
            contentView.containerDraw.left,
            contentView.containerDraw.top,
            contentView.containerDraw.right,
            contentView.containerDraw.bottom
        )
        contentView.containerDraw.draw(canvas)
//        val exportBM = containerDraw.drawToBitmap(Bitmap.Config.ARGB_8888)

        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        paint.isAntiAlias = true
        canvas.drawBitmap(nBitmap, 0.0f, 0.0f, paint)
        bitmap = nBitmap
        onDefectDrawListener?.onSaveDraw(bitmap)
    }

    private fun setNewColor(color: Int) {
        contentView.drawView.setColor(color)
    }

    private fun setBackgroundColorButtonColor(color: Int) {
        val drawableBackground = ResourcesCompat.getDrawable(resources,R.drawable.btn_radius, null)
        drawableBackground?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        contentView.btnColor.background = drawableBackground
    }

    @SuppressLint("CheckResult")
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