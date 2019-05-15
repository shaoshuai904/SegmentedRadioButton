package com.makeramen.segmented

import android.app.Activity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * show demo
 *
 * @author maple
 * @time 17/3/29
 */
class MainActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        srg_single.setOnCheckedChangeListener { _: RadioGroup, checkedId: Int ->
            if (checkedId == R.id.bt_single1) {
                showToast("一个")
            }
        }
        srg_bt.setOnCheckedChangeListener { _: RadioGroup, checkedId: Int ->
            if (checkedId == R.id.bt_view1) {
                showToast("111")
            } else if (checkedId == R.id.bt_view2) {
                showToast("222")
            }
        }

        initViewPagerListener()
        initCheckBoxListener()

    }

    private fun initViewPagerListener() {
        val ids = arrayListOf(
                R.id.button_add,
                R.id.button_call,
                R.id.button_camera
        )
        val views = arrayListOf<View>(
                getView(android.R.drawable.ic_menu_add),
                getView(android.R.drawable.ic_menu_call),
                getView(android.R.drawable.ic_menu_camera)
        )

        segment_img.setOnCheckedChangeListener { _: RadioGroup, checkedId: Int ->
            when (checkedId) {
                R.id.button_add -> {
                    vp_view_pager.currentItem = 0
                    showToast("Add")
                }
                R.id.button_call -> {
                    vp_view_pager.currentItem = 1
                    showToast("Call")
                }
                R.id.button_camera -> {
                    vp_view_pager.currentItem = 2
                    showToast("Camera")
                }
                else -> {
                }
            }
        }
        // ViewPager
        vp_view_pager.adapter = BasePagerAdapter(views)
        vp_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                segment_img.check(ids[position])
            }
        })

        // default setting
        segment_img.check(ids[0])

    }

    private fun initCheckBoxListener() {
        // SegmentedRadioGroup
        segment_text.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_one -> showToast("One")
                R.id.rb_two -> showToast("Two")
                R.id.rb_three -> showToast("Three")
                R.id.rb_fore -> showToast("Fore")
                else -> {
                }
            }
        }
        // CheckBox
        cb_one.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cb_one.text = "显示 one"
                rb_one.visibility = View.VISIBLE
            } else {
                cb_one.text = "隐藏 one"
                rb_one.visibility = View.GONE
            }
        }
        cb_two.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cb_two.text = "显示 two"
                rb_two.visibility = View.VISIBLE
            } else {
                cb_two.text = "隐藏 two"
                rb_two.visibility = View.GONE
            }
        }
        cb_three.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cb_three.text = "显示 three"
                rb_three.visibility = View.VISIBLE
            } else {
                cb_three.text = "隐藏 three"
                rb_three.visibility = View.GONE
            }
        }
        cb_fore.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cb_fore.text = "显示 fore"
                rb_fore.visibility = View.VISIBLE
            } else {
                cb_fore.text = "隐藏 fore"
                rb_fore.visibility = View.GONE
            }
        }
    }

    private fun getView(resId: Int): ImageView {
        val imageView = ImageView(this)
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.setImageResource(resId)
        return imageView
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}