package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Custom ViewPager that allows swiping to be disabled.
 */
class CalendarPager extends RtlViewPager {

    private boolean pagingEnabled = true;

    public CalendarPager(@NonNull final Context context) {
        super(context);
    }

    public CalendarPager(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @return is this viewpager allowed to page
     */
    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    /**
     * enable disable viewpager scroll
     *
     * @param pagingEnabled false to disable paging, true for paging (default)
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return pagingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO: There is an issue inside ViewPager class so that the infoForCurrentScrollPosition() function
        //  return null value of the current item scrolling position. This happened when the user wanted to
        //  click on calendar day and scrolling to next month of the calendar. We can enhance this issue later.
        try {
            return pagingEnabled && super.onTouchEvent(ev);
        } catch (NullPointerException exception) {
            return false;
        }
    }

    @Override
    public boolean canScrollVertically(int direction) {
        /**
         * disables scrolling vertically when paging disabled, fixes scrolling
         * for nested {@link ViewPager}
         */
        return pagingEnabled && super.canScrollVertically(direction);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        /**
         * disables scrolling horizontally when paging disabled, fixes scrolling
         * for nested {@link ViewPager}
         */
        return pagingEnabled && super.canScrollHorizontally(direction);
    }
}
