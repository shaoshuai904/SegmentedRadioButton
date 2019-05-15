package com.makeramen.segmented.custom

import android.content.Context
import android.util.AttributeSet
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

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        changeButtonsImages()
    }

    private fun changeButtonsImages() {
        // set padding 1px
        this.setBackgroundResource(R.drawable.segment_radio_single)
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
            showList[0].setBackgroundResource(R.drawable.segment_radio_left)
            for (i in 1 until count - 1) {
                showList[i].setBackgroundResource(R.drawable.segment_radio_middle)
            }
            showList[count - 1].setBackgroundResource(R.drawable.segment_radio_right)
        } else if (count == 1) {// 只有一个，纯粹是二逼
            showList[0].setBackgroundResource(R.drawable.segment_radio_single)
        }
    }
}