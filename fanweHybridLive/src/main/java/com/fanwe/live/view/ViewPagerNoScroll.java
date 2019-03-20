package com.fanwe.live.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/9/5 0005.
 */

public class ViewPagerNoScroll extends ViewPager {
        //是否可以进行滑动
        private boolean isSlide = false;

        public void setSlide(boolean slide) {
            isSlide = slide;
        }
        public ViewPagerNoScroll(Context context) {
            super(context);
        }

        public ViewPagerNoScroll(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return isSlide;
        }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
