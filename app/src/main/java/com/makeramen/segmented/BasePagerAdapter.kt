package com.makeramen.segmented

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * @author maple
 * @time 2018/11/14
 */
class BasePagerAdapter(var views: List<View>) : PagerAdapter() {

    override fun getCount() = views.size

    override fun isViewFromObject(view: View, obj: Any) = (view == obj)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

}
