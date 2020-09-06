package com.mg.axechen.libcommon

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import java.lang.Exception

object DensityUtil {

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun dp2px(context: Context, dpValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.resources.displayMetrics)
    }

    fun sp2px(context: Context, spValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics)
    }

    fun screenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun screenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun screenScale(context: Context): Float {
        return screenWidth(context).toFloat() / screenHeight(context).toFloat()
    }

    fun viewWidth(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredWidth
    }

    fun viewHeight(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
        return view.measuredHeight
    }

    fun getViewLocationInWindow(view: View): IntArray {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return location
    }

    fun getViewLocationInScreen(view: View): IntArray {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return location
    }

    fun getStatusHeight(activity: Activity): Int {
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }

    fun getDeviceSize(context: Context): Double {
        val dm = context.resources.displayMetrics
        val sqrt = Math.sqrt(Math.pow(dm.widthPixels.toDouble(), 2.0) + Math.pow(dm.heightPixels.toDouble(), 2.0))
        return sqrt / (160 * dm.density)
    }

    private fun getDisplayMetrics(context: Context?): DisplayMetrics {
        if (context == null) {
            throw IllegalArgumentException("context can't null")
        }
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        return displayMetrics
    }

    fun getWindowWidth(context: Context): Int {
        val metrics = getDisplayMetrics(context)
        return metrics.widthPixels
    }

    fun getWindowHeight(context: Context): Int {
        val metrics = getDisplayMetrics(context)
        return metrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getNavBarHeight(c: Context): Int {
        var result = 0
        val resources = c.resources
        val orientation = c.resources.configuration.orientation
        val resourceId: Int
        resourceId = if (isTablet(c)) {
            resources.getIdentifier(if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height" else "navigation_bar_height_landscape", "dimen", "android")
        } else {
            resources.getIdentifier(if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height" else "navigation_bar_width", "dimen", "android")
        }
        if (resourceId > 0) {
            result = c.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    //是否是平板
    private fun isTablet(c: Context): Boolean {
        return c.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }
}
