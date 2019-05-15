package com.makeramen.segmented.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

import com.makeramen.segmented.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 分段式 RadioGroup
 *
 * @author maple
 * @time 17/3/29
 */
public class SegmentedRadioGroup extends RadioGroup {

    public SegmentedRadioGroup(Context context) {
        super(context);
    }

    public SegmentedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        changeButtonsImages();
    }

    private void changeButtonsImages() {
        // set padding 1px
        this.setBackgroundResource(R.drawable.segment_radio_single);
        int line = (int) getResources().getDimension(R.dimen.divide_line);
        this.setPadding(line, line, line, line);

        // get visibility item view
        List<View> showList = new ArrayList<View>();
        for (int i = 0; i < super.getChildCount(); i++) {
            View view = super.getChildAt(i);
            if (view.getVisibility() == View.VISIBLE) {
                showList.add(view);
            }
        }

        // set item background
        int count = showList.size();
        if (count > 1) {
            showList.get(0).setBackgroundResource(R.drawable.segment_radio_left);
            for (int i = 1; i < count - 1; i++) {
                showList.get(i).setBackgroundResource(R.drawable.segment_radio_middle);
            }
            showList.get(count - 1).setBackgroundResource(R.drawable.segment_radio_right);
        } else if (count == 1) {// 只有一个，纯粹是二逼
            showList.get(0).setBackgroundResource(R.drawable.segment_radio_single);
        }
    }
}