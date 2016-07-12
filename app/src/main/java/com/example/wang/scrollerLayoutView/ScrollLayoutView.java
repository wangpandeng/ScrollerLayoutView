package com.example.wang.scrollerLayoutView;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wang on 2016/7/12.
 */
public class ScrollLayoutView extends ViewGroup {
    //view的最小滑动距离，（如果小于距离，默认没有滑动）
    private int mTouchSlop;
    //最后一次X坐标
    private int mLastX;
    private int rightBorder;
    private int leftBorder;
    private Scroller mScroll;

    public ScrollLayoutView(Context context) {
        super(context);
        init(context);
    }

    public ScrollLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroll = new Scroller(context);
        ViewConfiguration mConfiguration = ViewConfiguration.get(context);
        //最小滑动距离
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(mConfiguration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) *
                    childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(childCount - 1).getRight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int mDownX = 0;
        int mAction = ev.getAction();
        switch (mAction) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getRawX();
                mLastX = mDownX;
                break;
            case MotionEvent.ACTION_MOVE:
                int mMoveX = (int) ev.getRawX();
                Log.i("moveX", "mMoveX:" + mMoveX);
                mLastX = mMoveX;
                int diff = Math.abs(mMoveX - mDownX);
                //如果滑距离大于最小滑动值mTouchSlop,则拦截事件，不让他让子View传递。
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        //默认是false，不进行拦截，往子View传递。
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int mAction = event.getAction();
        switch (mAction) {
//            case MotionEvent.ACTION_DOWN:
//                int mDownX = (int) event.getRawX();
//                mLastX = mDownX;
//                break;
            case MotionEvent.ACTION_MOVE:
                int mMoveX = (int) event.getRawX();
                int scrolledX = mLastX - mMoveX;

                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }

                scrollBy(scrolledX, 0);
                mLastX = mMoveX;
                break;
            case MotionEvent.ACTION_UP:
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                mScroll.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroll.computeScrollOffset()) {
            scrollTo(mScroll.getCurrX(), mScroll.getCurrY());
            invalidate();
        }
    }
}
