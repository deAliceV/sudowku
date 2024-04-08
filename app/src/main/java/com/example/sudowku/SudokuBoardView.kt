package com.example.sudowku

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SudokuBoardView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {
    private var sqrtSize = 3
    private var size = 9
    private var cellSizePixels = 0F
    private var selectedRow = 0
    private var selectedCol = 0
    private val thickLinePaint = Paint().apply{
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 6f
    }
    private val thinLinePaint = Paint().apply{
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 3f
    }
    private val selectedCellPaint = Paint().apply{
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6ead3b")
    }
    private val conflictingCellPaint = Paint().apply{
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#efedef")
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }
    override fun onDraw(canvas: Canvas){
        cellSizePixels = (width / size).toFloat()
        fillCells(canvas)
        drawLines(canvas)
    }
    private fun fillCells(canvas: Canvas){
        if(selectedRow == -1 || selectedCol == -1)return
        for(r in 0..size){
            for(c in 0..size){
                if(r==selectedRow&&c==selectedCol){
                    fillCell(canvas, r, c, selectedCellPaint)
                }else if(r==selectedRow || c==selectedCol){
                    fillCell(canvas, r, c, conflictingCellPaint)
                }else if(r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize){
                    fillCell(canvas, r, c, conflictingCellPaint)
                }
            }
        }
    }
    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint){
        canvas.drawRect(c*cellSizePixels,r*cellSizePixels, (c+1)*cellSizePixels, (r + 1) * cellSizePixels, paint)
    }
    private fun drawLines(canvas: Canvas){
        canvas.drawRect(0F,0F, width.toFloat(), height.toFloat(), thickLinePaint)
        for (i in 1 until size){
            val paintToUse = when(i%sqrtSize){
                0->thickLinePaint
                else -> thinLinePaint
            }
            canvas.drawLine(
                i*cellSizePixels,
                0F,
                i*cellSizePixels,
                height.toFloat(),
                paintToUse
            )
            canvas.drawLine(
                0F,
                i*cellSizePixels,
                width.toFloat(),
                i*cellSizePixels,
                paintToUse
            )

        }
    }
}