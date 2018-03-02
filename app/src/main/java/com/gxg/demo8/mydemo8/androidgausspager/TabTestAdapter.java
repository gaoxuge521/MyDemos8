package com.gxg.demo8.mydemo8.androidgausspager;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.demo8.mydemo8.R;

import java.util.List;

/**
 * 作者：Administrator on 2018/3/2 10:10
 * 邮箱：android_gaoxuge@163.com
 */
public class TabTestAdapter  extends BaseQuickAdapter<String,BaseViewHolder>{
    public TabTestAdapter(@Nullable List<String> data) {
        super(R.layout.item_rv_test,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_test,item);
    }
}
