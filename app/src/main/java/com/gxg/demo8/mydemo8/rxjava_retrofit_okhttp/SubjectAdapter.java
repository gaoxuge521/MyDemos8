package com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxg.demo8.mydemo8.R;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.bean.MovieSubject;

import java.util.List;

/**
 * 作者：Administrator on 2017/11/6 13:07
 * 邮箱：android_gaoxuge@163.com
 */
public class SubjectAdapter extends BaseQuickAdapter<MovieSubject.SubjectsBean,BaseViewHolder> {
    public SubjectAdapter( @Nullable List<MovieSubject.SubjectsBean> data) {
        super(R.layout.item_subject, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieSubject.SubjectsBean item) {

        ImageView imageView = helper.getView(R.id.iv_img);
        Glide.with(mContext).load(item.getImages().getSmall()).into(imageView);

        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_name,item.getOriginal_title());
        helper.setText(R.id.tv_time,"上映时间："+item.getYear());
    }
}
