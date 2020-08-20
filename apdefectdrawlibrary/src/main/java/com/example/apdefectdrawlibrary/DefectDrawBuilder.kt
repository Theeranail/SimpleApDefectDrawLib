package com.example.apdefectdrawlibrary

class DefectDrawBuilder {
    private var imagePath: String = ""
    private var onDefectDrawListener: OnDefectDrawListener? = null

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

    fun defectDrawListener(onDefectDrawListener: OnDefectDrawListener): DefectDrawBuilder {
        this.onDefectDrawListener = onDefectDrawListener
        return this
    }

    fun build(apDefectDraw: APDefectDraw): APDefectDraw {
        apDefectDraw.imagePath = imagePath
        apDefectDraw.onDefectDrawListener = onDefectDrawListener
        return apDefectDraw.initViewImage()
    }
}