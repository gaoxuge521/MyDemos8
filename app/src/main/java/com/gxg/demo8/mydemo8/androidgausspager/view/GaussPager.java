package com.gxg.demo8.mydemo8.androidgausspager.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.gxg.demo8.mydemo8.R;
import com.gxg.demo8.mydemo8.androidgausspager.util.FastBlur;


public class GaussPager extends LinearLayout {

    private ViewGroup mTop;
    private View mNav;
    private ViewPager mViewPager;

    private int mTopViewHeight;
    private ViewGroup mInnerScrollView; //这是下方控件内部的ScrollView
    private boolean isTopHidden = false;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;

    private boolean isInControl = false;

    private Bitmap bg;
    private Bitmap gaussBg;
    private float gaussPercent = 0;
    private ImageView bgLayer;
    private ImageView blurImageView;

    private Handler UIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            blurImageView.setImageBitmap(gaussBg);
            mTop.setBackgroundDrawable(new BitmapDrawable(gaussBg));
        }
    };

    public GaussPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = (ViewGroup) findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException(
                    "id_stickynavlayout_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager) view;
        bgLayer = (ImageView) findViewById(R.id.bglayer);
        bg = ((BitmapDrawable) bgLayer.getBackground()).getBitmap();
        //事先进行异步模糊
        new BlurTask().execute();
        //将模糊层覆盖在普通层之上
        blurImageView = new ImageView(getContext());
        blurImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        blurImageView.setAlpha(0f);
        blurImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mTop.addView(blurImageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //测量可滑动长度，以便计算模糊程度和判断其是否隐藏
        mTopViewHeight = mTop.getMeasuredHeight();
    }


    /**
     * 判断事件究竟由谁处理,如果返回true，事件不会传递到子View上
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();
                //偏移量超过临界值，判定为滑动,返回true表示拦截，即：整体（两个View）一起滑动的情况
                if (Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                    if (mInnerScrollView instanceof ScrollView) {
                        // 如果topView没有隐藏
                        // 或sc的scrollY = 0 && topView隐藏 && 下拉，则拦截
                        if (!isTopHidden
                                || (mInnerScrollView.getScrollY() == 0
                                && isTopHidden && dy > 0)) {

                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    } else if (mInnerScrollView instanceof ListView) {

                        ListView lv = (ListView) mInnerScrollView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        // 如果topView没有隐藏
                        // 或sc的listView在顶部 && topView隐藏 && 下拉，则拦截

                        if (!isTopHidden || //
                                (c != null //
                                        && c.getTop() == 0//
                                        && isTopHidden && dy > 0)) {

                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }

                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragging = false;
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 得到下方元素的滑动View，传给InnerScrollView
     */
    private void getCurrentScrollView() {
        int currentItem = mViewPager.getCurrentItem();
        PagerAdapter a = mViewPager.getAdapter();
        if (a instanceof FragmentPagerAdapter) {
            FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
            Fragment item = (Fragment) fadapter.instantiateItem(mViewPager,
                    currentItem);
            mInnerScrollView = (ViewGroup) (item.getView()
                    .findViewById(R.id.id_stickynavlayout_innerscrollview));
        } else if (a instanceof FragmentStatePagerAdapter) {
            FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
            Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager,
                    currentItem);
            mInnerScrollView = (ViewGroup) (item.getView()
                    .findViewById(R.id.id_stickynavlayout_innerscrollview));
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //整个ViewGroup 整体的滚动
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;

//                Log.e("TAG", "dy = " + dy + " , y = " + y + " , mLastY = " + mLastY);

                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }
                if (mDragging) {
                    //核心滑动方法
                    scrollBy(0, (int) -dy);
                    // 如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
                    // 处理手指一路下拉的情况
                    if (getScrollY() == mTopViewHeight && dy < 0) {
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                    }
                }

                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                recycleVelocityTracker();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                //开始滑行
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    /**
     * 调用scrollBy会调用到这个方法
     *
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);

        }
        if (mTopViewHeight - getScaleY() < mTopViewHeight / 2) {
            return;
        }
        gaussPercent = ((float) getScrollY() / (mTopViewHeight - mTopViewHeight / 2));
        isTopHidden = getScrollY() == mTopViewHeight;
        float alpha = gaussPercent;
        //根据模糊度进行可见度调整
        blurImageView.setAlpha(alpha);
        bgLayer.setAlpha(1 - alpha);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            //这句话调用后会继续调用computeScroll
            invalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    class BlurTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            gaussBg = new FastBlur().fastblur(bg, 30, blurImageView);
            UIHandler.sendEmptyMessage(1);
            return null;
        }

    }

}
