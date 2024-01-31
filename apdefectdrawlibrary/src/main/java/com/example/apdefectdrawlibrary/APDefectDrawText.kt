package com.example.apdefectdrawlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class APDefectDrawText : View, View.OnClickListener {

    private lateinit var bitmap: Bitmap
    private var path: Path = Path()
    private lateinit var paint: Paint

    var strokeColor: Int = Color.BLACK
    var strokeWidth: Float = 20.0f
    var onDefectDrawListener: OnDefectDrawListener? = null

    override fun onClick(v: View?) {
    }

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
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_temp3)
        paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = strokeColor
        paint.strokeWidth = strokeWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null)
        canvas.drawPath(path, paint)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                path.lineTo(x, y)

                    val nBitmap =
                        Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
                    val canvas = Canvas(nBitmap)
                    val nPaint = Paint()
                    canvas.drawPath(path, nPaint)
                    nPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, nPaint)
                    bitmap = nBitmap
                    onDefectDrawListener?.onSaveDraw(bitmap)
                    invalidate()

            }
        }
        invalidate()
        return true
    }


    fun exportBitmap() {
        Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas()
        val nPaint = Paint()
        canvas.drawPath(path, nPaint)
        nPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, nPaint)
//            bitmap = nBitmap
        onDefectDrawListener?.onSaveDraw(bitmap)
        invalidate()
    }
}