package com.example.apdefectdrawlibrary

import android.graphics.Color

class DefectDrawBuilder {
    private var imagePath: String = ""
    private var strokeWidth: Float = 2.0f
    private var onDefectDrawListener: OnDefectDrawListener? = null
    private var isShowButtonSave: Boolean = true
    private var isShowTools: Boolean = true
    private var strokeColor: Int = Color.BLACK
    private var backgroundColorId:Int = R.color.color_white

    companion object {
        @JvmStatic
        fun instances(): DefectDrawBuilder {
            return DefectDrawBuilder()
        }
    }

    fun imagePath(imagePath: String): DefectDrawBuilder {
        this.imagePath = imagePath
        return this
    }

    fun strokeWidth(strokeWidth: Float): DefectDrawBuilder {
        this.strokeWidth = strokeWidth
        return this
    }

    fun defectDrawListener(onDefectDrawListener: OnDefectDrawListener): DefectDrawBuilder {
        this.onDefectDrawListener = onDefectDrawListener
        return this
    }

    fun isShowButtonSave(isShowButtonSave: Boolean): DefectDrawBuilder {
        this.isShowButtonSave = isShowButtonSave
        return this
    }

    fun isShowTools(isShowTools: Boolean): DefectDrawBuilder {
        this.isShowTools = isShowTools
        return this
    }

    fun setStrokeColor(color: Int): DefectDrawBuilder {
        this.strokeColor = color
        return this
    }

    fun setBackgroundColor(color: Int): DefectDrawBuilder {
        this.backgroundColorId = color
        return this
    }

    fun build(apDefectDraw: APDefectDraw): APDefectDraw {
        apDefectDraw.imagePath = imagePath
        apDefectDraw.onDefectDrawListener = onDefectDrawListener
        apDefectDraw.setColorStroke(strokeColor)
        apDefectDraw.setStrokeWith(strokeWidth)
        apDefectDraw.isShowButtonSave(isShowButtonSave)
        apDefectDraw.isShowTools(isShowTools)
        apDefectDraw.setBackgroundColorDraw(backgroundColorId)
        return apDefectDraw.initViewImage()
    }
}