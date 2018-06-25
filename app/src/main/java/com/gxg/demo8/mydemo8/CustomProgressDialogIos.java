package com.gxg.demo8.mydemo8;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/********************************************************************
 * [Summary]
 *       TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * [Remarks]
 *       TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 *******************************************************************/
@SuppressWarnings("unused")
public class CustomProgressDialogIos extends Dialog {
    private Context context = null;

    private boolean isCancelable=false;
    private boolean isCancelOutside=false;
    private boolean isShowMessage=true;
    public CustomProgressDialogIos(Context context) {
        super(context, R.style.CustomProgressDialogIos);
        this.context = context;
        setContentView(R.layout.dialog_loading);
        getWindow().getAttributes().gravity = Gravity.CENTER;


    }




    public void onWindowFocusChanged(boolean hasFocus) {
        TextView tvMsg = (TextView) findViewById(R.id.tipTextView);


    }
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    /**
     * 设置是否显示提示信息
     * @param isShowMessage
     * @return
     */
    public void setShowMessage(boolean isShowMessage){
        this.isShowMessage=isShowMessage;
    }
    /**
     * [Summary]
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public void setMessage(String strMessage) {
        TextView tvMsg = (TextView) findViewById(R.id.tipTextView);

        if (tvMsg != null) {
            if(isShowMessage && !TextUtils.isEmpty(strMessage)){
                tvMsg.setText(strMessage);
            }else{
                tvMsg.setVisibility(View.GONE);
            }
        }

    }


    /**
     * 设置是否可以取消
     * @param isCancelOutside
     * @return
     */
    public void setCancelOutside(boolean isCancelOutside){
        this.isCancelOutside=isCancelOutside;
    }
}

