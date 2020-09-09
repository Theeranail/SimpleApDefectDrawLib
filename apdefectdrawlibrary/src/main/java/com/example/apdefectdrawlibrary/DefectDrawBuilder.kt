package com.example.apdefectdrawlibrary

class DefectDrawBuilder {
    private var imagePath: String = ""
    private var strokeWidth: Float = 2.0f
    private var onDefectDrawListener: OnDefectDrawListener? = null
    private var isShowButtonSave: Boolean = true;

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

    fun build(apDefectDraw: APDefectDraw): APDefectDraw {
        apDefectDraw.imagePath = imagePath
        apDefectDraw.strokeWidth = strokeWidth
        apDefectDraw.onDefectDrawListener = onDefectDrawListener
        apDefectDraw.isShowButtonSave(isShowButtonSave)
        return apDefectDraw.initViewImage()
    }
}