package com.makeramen.segmented.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.StateSet
import android.view.View
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
    var mBarStrokeWidth: Int = 1 // dp

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SegmentTabLayout)

        mBarColor = ta.getColor(R.styleable.SegmentTabLayout_bar_color, mBarColor)
        mBarCheckedColor = ta.getColor(R.styleable.SegmentTabLayout_bar_checked_color, mBarCheckedColor)
        mBarPressedColor = ta.getColor(R.styleable.SegmentTabLayout_bar_pressed_color, mBarPressedColor)
        mBarStrokeWidth = ta.getDimension(R.styleable.SegmentTabLayout_bar_stroke_width, 1f).toInt()

        ta.recycle()
    }

//    override fun onFinishInflate() {
//        super.onFinishInflate()
//    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        changeButtonsImages()
    }

    private fun changeButtonsImages() {
        // set padding 1px
        this.background = getRadioDrawable(R.drawable.shape_radio_single)
        val line = resources.getDimension(R.dimen.divide_line).toInt()
        this.setPadding(line, line, line, line)

        // get visibility item view
        val showList = ArrayList<View>()
        for (i in 0 until super.getChildCount()) {
            val view = super.getChildAt(i)
            if (view.visibility == View.VISIBLE) {
                showList.add(view)
            }
        }
        // set item background
        val count = showList.size
        if (count > 1) {
            showList[0].background = getRadioDrawable(R.drawable.shape_radio_left)
            for (i in 1 until count - 1) {
                showList[i].background = getRadioDrawable(R.drawable.shape_radio_middle)
            }
            showList[count - 1].background = getRadioDrawable(R.drawable.shape_radio_right)
        } else if (count == 1) {// 只有一个，纯粹是二逼
            showList[0].background = getRadioDrawable(R.drawable.shape_radio_single)
        }
    }

    /**
     * get selector drawable
     */
    private fun getRadioDrawable(@DrawableRes shapeRes: Int): StateListDrawable {
        val selectorDrawable = StateListDrawable()
        selectorDrawable.addState(intArrayOf(android.R.attr.state_pressed), setPressedColor(shapeRes))
        selectorDrawable.addState(intArrayOf(android.R.attr.state_checked), setCheckedColor(shapeRes))
        selectorDrawable.addState(StateSet.WILD_CARD, setDefaultColor(shapeRes))
        return selectorDrawable
    }

    private fun setPressedColor(@DrawableRes shapeRes: Int): GradientDrawable {
        val shape: GradientDrawable = ContextCompat.getDrawable(context, shapeRes)?.mutate() as GradientDrawable
        shape.setColor(mBarPressedColor)
        shape.setStroke(mBarStrokeWidth, mBarPressedColor)
        return shape
    }

    private fun setCheckedColor(@DrawableRes shapeRes: Int): GradientDrawable {
        val shape: GradientDrawable = ContextCompat.getDrawable(context, shapeRes)?.mutate() as GradientDrawable
        shape.setColor(mBarCheckedColor)
        shape.setStroke(mBarStrokeWidth, mBarPressedColor)
        return shape
    }

    private fun setDefaultColor(@DrawableRes shapeRes: Int): GradientDrawable {
        val shape: GradientDrawable = ContextCompat.getDrawable(context, shapeRes)?.mutate() as GradientDrawable
        shape.setColor(mBarColor)
        shape.setStroke(mBarStrokeWidth, mBarPressedColor)
        return shape
    }

}

