package com.gxg.demo8.mydemo8.smartRefresh;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.internal.pathview.PathsView;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * 作者：Administrator on 2017/11/7 11:34
 * 邮箱：android_gaoxuge@163.com
 */
public class MyHead extends LinearLayout implements RefreshHeader {

    private TextView mHeadText;//标题文本
    private ProgressDrawable progressDrawable;//刷新动画
    private PathsView mArrowView;//下拉箭头
    private ImageView mProgressView;//刷新动画视图

    public MyHead(Context context) {
        super(context);
        init(context);


    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        mHeadText = new TextView(context);
        progressDrawable = new ProgressDrawable();
        mArrowView = new PathsView(context);
        mArrowView.parserPaths("M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z");
        mProgressView = new ImageView(context);
        mProgressView.setImageDrawable(progressDrawable);

        addView(mProgressView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mArrowView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(new View(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mHeadText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    public MyHead(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyHead(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    /**
     * 获取真实视图（必须返回，不能为null）
     */
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    /**
     * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
     */
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }


    /**
     * 开始动画（开始刷新或者开始加载动画）
     * @param layout RefreshLayout
     * @param height HeaderHeight or FooterHeight
     * @param extendHeight extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        progressDrawable.start();
    }
    /**
     * 动画结束
     * @param layout RefreshLayout
     * @param success 数据是否成功刷新或加载
     * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
     */
    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        progressDrawable.stop();
        if(success){
            mHeadText.setText("刷新完成");
        }else{
            mHeadText.setText("刷新失败");
        }
        return 500;
    }



    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

        switch (newState){
            case None:
            case PullDownToRefresh:
                mHeadText.setText("下拉开始刷新");
                mArrowView.setVisibility(View.VISIBLE);//显示下拉箭头
                mProgressView.setVisibility(View.GONE);//隐藏动画
                mArrowView.animate().rotation(0);//还原箭头方向
                break;
            case Refreshing:
                mHeadText.setText("正在刷新");
                mArrowView.setVisibility(View.GONE);//显示下拉箭头
                mProgressView.setVisibility(View.VISIBLE);//隐藏动画
                break;
            case ReleaseToRefresh:
                mHeadText.setText("释放完立即刷新");
                mArrowView.animate().rotation(180);
                break;
        }
    }

    /**
     * 尺寸定义初始化完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
     * @param kernel RefreshKernel 核心接口（用于完成高级Header功能）
     * @param height HeaderHeight or FooterHeight
     * @param extendHeight extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
    /**
     * 手指拖动下拉（会连续多次调用，用于实时控制动画关键帧）
     * @param percent 下拉的百分比 值 = offset/headerHeight (0 - percent - (headerHeight+extendHeight) / headerHeight )
     * @param offset 下拉的像素偏移量  0 - offset - (headerHeight+extendHeight)
     * @param headerHeight Header的高度
     * @param extendHeight Header的扩展高度
     */
    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

    }

    /**
     * 手指释放之后的持续动画（会连续多次调用，用于实时控制动画关键帧）
     * @param percent 下拉的百分比 值 = offset/headerHeight (0 - percent - (headerHeight+extendHeight) / headerHeight )
     * @param offset 下拉的像素偏移量  0 - offset - (headerHeight+extendHeight)
     * @param headerHeight Header的高度
     * @param extendHeight Header的扩展高度
     */
    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }
    /**
     * 设置主题颜色 （如果自定义的Header没有注意颜色，本方法可以什么都不处理）
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Override
    public void setPrimaryColors(@ColorInt int... colors) {

    }
}
