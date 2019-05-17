package com.makeramen.segmented.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatRadioButton
import android.util.AttributeSet
import com.makeramen.segmented.R


/**
 * 图片居中的 RadioButton
 *
 * @author maple
 * @time 2017/3/29
 */
class CenteredRadioImageButton : AppCompatRadioButton {

    var image: Drawable? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.MsButton, 0, 0)

        try {
            image = a.getDrawable(R.styleable.MsButton_msButton)
        } finally {
            setButtonDrawable(android.R.color.transparent)
            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (image != null) {
            image!!.state = drawableState

            // 缩放图像以适应里面的按钮
            val imgHeight = image!!.intrinsicHeight
            val imgWidth = image!!.intrinsicWidth
            val scale: Float

            scale = if (imgWidth <= width && imgHeight <= height) {
                1.0f
            } else {
                Math.min(width.toFloat() / imgWidth.toFloat(),
                        height.toFloat() / imgHeight.toFloat())
            }

            val dx = ((width - imgWidth * scale) * 0.5f + 0.5f).toInt()
            val dy = ((height - imgHeight * scale) * 0.5f + 0.5f).toInt()

            image!!.setBounds(dx, dy,
                    (dx + imgWidth * scale).toInt(),
                    (dy + imgHeight * scale).toInt())

            image!!.draw(canvas)
        }
    }
}