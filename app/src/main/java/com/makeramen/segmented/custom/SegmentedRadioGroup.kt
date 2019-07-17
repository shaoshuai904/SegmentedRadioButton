package com.makeramen.segmented.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.util.StateSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.makeramen.segmented.R
import java.util.*

/**
 * 分段式 RadioGroup
 *
 * @author maple
 * @time 2017/3/29
 */
class SegmentedRadioGroup : RadioGroup {
    @ColorInt
    var mBarColor: Int = Color.parseColor("#EAEAEA")
    @ColorInt
    var mBarCheckedColor: Int = Color.parseColor("#6B7478")
    @ColorInt
    var mBarPressedColor: Int = Color.parseColor("#525658")
    private var mBarStrokeWidth: Int = 1 // px
    private var mBarRadius = 1F

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttributes(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun obtainAttributes(context: Context, attrs: AttributeSet) {
        setWillNotDraw(false)//重写onDraw方法,需要调用这个方法来清除flag
//        clipChildren = false
//        clipToPadding = false

        val ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentRadioGroup)

        mBarColor = ta.getColor(R.styleable.SegmentRadioGroup_bar_color, mBarColor)
        mBarCheckedColor = ta.getColor(R.styleable.SegmentRadioGroup_bar_checked_color, mBarCheckedColor)
        mBarPressedColor = ta.getColor(R.styleable.SegmentRadioGroup_bar_pressed_color, mBarPressedColor)
        mBarStrokeWidth = ta.getDimension(R.styleable.SegmentRadioGroup_bar_stroke_width, resources.getDimension(R.dimen.divide_line)).toInt()
        mBarRadius = resources.getDimension(R.dimen.radius_value)
        ta.recycle()
    }

//    override fun onFinishInflate() {
//        super.onFinishInflate()
//    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        changeButtonsImages()
    }

    private val showList = ArrayList<View>()

    private fun changeButtonsImages() {
//        this.background = getRadioShape(RadioPosition.Single).apply {
//            setStroke(mBarStrokeWidth, Color.YELLOW)
//        }

        // get visibility item view
        showList.clear()
        for (i in 0 until super.getChildCount()) {
            val view = super.getChildAt(i)
            if (view.visibility == View.VISIBLE) {
                showList.add(view)
            }
        }
        // set item background
        val margin = mBarStrokeWidth
        val count = showList.size
        if (count > 1) {
            showList[0].apply {
                background = getRadioDrawable(RadioPosition.Left)
                layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                    setMargins(margin, margin, margin / 2, margin)
                }
            }
            for (i in 1 until count - 1) {
                showList[i].apply {
                    background = getRadioDrawable(RadioPosition.Middle)
                    layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                        setMargins(margin / 2, margin, margin / 2, margin)
                    }
                }
            }
            showList[count - 1].apply {
                background = getRadioDrawable(RadioPosition.Right)
                layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                    setMargins(margin / 2, margin, margin, margin)
                }
            }
        } else if (count == 1) {
            showList[0].apply {
                background = getRadioDrawable(RadioPosition.Single)
                layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                    setMargins(margin, margin, margin, margin)
                }
            }
        }
    }

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isInEditMode) {
            return
        }

        paint.strokeWidth = mBarStrokeWidth.toFloat()
        paint.color = mBarCheckedColor
        // draw 边框
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas?.drawRoundRect(paddingLeft.toFloat(), 0F, (paddingLeft + width).toFloat(), height.toFloat(),
                    mBarRadius, mBarRadius, paint)
        } else { // tagAPI < 21

        }
        // draw divider 分割线
        val padding = mBarStrokeWidth.toFloat()
        if (showList.size > 1) {
            for (i in 0..showList.size - 2) {
                val tab = showList[i]
                val startX = (paddingLeft + tab.right + mBarStrokeWidth / 2).toFloat()
                canvas?.drawLine(startX, padding, startX, height - padding, paint)
            }
            Log.e("index", "${showList.size}")
        }
    }

    //-------------------- radio shape ------------------------

    /**
     * 按钮位置
     */
    enum class RadioPosition {
        Single, Left, Middle, Right
    }

    private fun getRadioDrawable(position: RadioPosition): StateListDrawable {
        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed),
                    getRadioShape(position).apply { setColor(mBarPressedColor) })
            addState(intArrayOf(android.R.attr.state_checked),
                    getRadioShape(position).apply { setColor(mBarCheckedColor) })
            addState(StateSet.WILD_CARD,
                    getRadioShape(position).apply { setColor(mBarColor) })
        }
    }

    private fun getRadioShape(position: RadioPosition): GradientDrawable {
        return GradientDrawable().apply {
            //    <corners android:radius="@dimen/angle_value" />
            cornerRadii = when (position) {
                RadioPosition.Left -> setRadius(mBarRadius, 0F)
                RadioPosition.Right -> setRadius(0F, mBarRadius)
                RadioPosition.Middle -> setRadius(0F, 0F)
                RadioPosition.Single -> setRadius(mBarRadius, mBarRadius)
            }
            //    <solid android:color="@color/segment_btn_def" />
            setColor(mBarColor)
            //    <stroke
            //    android:width="@dimen/divide_line"
            //    android:color="@color/segment_btn_line" />
            // setStroke(mBarStrokeWidth, mBarPressedColor)
        }
    }

    private val mRadiusArr = FloatArray(8)

    /**
     * The corners are ordered top-left, top-right, bottom-right, bottom-left
     */
    private fun setRadius(leftRadius: Float, rightRadius: Float): FloatArray {
        mRadiusArr[0] = leftRadius
        mRadiusArr[1] = leftRadius
        mRadiusArr[2] = rightRadius
        mRadiusArr[3] = rightRadius
        mRadiusArr[4] = rightRadius
        mRadiusArr[5] = rightRadius
        mRadiusArr[6] = leftRadius
        mRadiusArr[7] = leftRadius
        return mRadiusArr
    }
}

