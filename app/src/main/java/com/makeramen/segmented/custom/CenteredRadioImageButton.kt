package com.makeramen.segmented.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatRadioButton
import android.util.AttributeSet
import com.makeramen.segmented.R
import kotlin.math.min

/**
 * 图片居中的 RadioButton
 *
 * @author maple
 * @time 2017/3/29
 */
class CenteredRadioImageButton : AppCompatRadioButton {
    private var mImage: Drawable? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttributes(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun obtainAttributes(context: Context, attrs: AttributeSet) {
        val styleAttr = context.obtainStyledAttributes(attrs, R.styleable.MsButton)
        try {
            mImage = styleAttr.getDrawable(R.styleable.MsButton_ms_drawable)
        } finally {
            setButtonDrawable(android.R.color.transparent)
        }
        styleAttr.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mImage != null) {
            mImage?.state = drawableState

            // 缩放图像以适应里面的按钮
            val imgHeight = mImage!!.intrinsicHeight
            val imgWidth = mImage!!.intrinsicWidth
            val scale: Float

            scale = if (imgWidth <= width && imgHeight <= height) {
                1.0f
            } else {
                min(width.toFloat() / imgWidth.toFloat(), height.toFloat() / imgHeight.toFloat())
            }

            val dx = ((width - imgWidth * scale) * 0.5f + 0.5f).toInt()
            val dy = ((height - imgHeight * scale) * 0.5f + 0.5f).toInt()

            mImage?.setBounds(dx, dy,
                    (dx + imgWidth * scale).toInt(),
                    (dy + imgHeight * scale).toInt())

            mImage?.draw(canvas)
        }
    }
}