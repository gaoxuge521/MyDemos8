package com.gxg.demo8.mydemo8.zidingyidialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gxg.demo8.mydemo8.R;

/**
 * 作者：Administrator on 2018/3/2 13:32
 * 邮箱：android_gaoxuge@163.com
 */
public class ErrorDialog  extends Dialog{
    private Context mContext;

    public ErrorDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }



    public ErrorDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected ErrorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, R.layout.dialog_error,null);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.height = dip2px(250);
        lp.width = dip2px(200);
        win.setAttributes(lp);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
