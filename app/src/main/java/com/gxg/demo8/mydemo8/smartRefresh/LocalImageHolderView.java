package com.gxg.demo8.mydemo8.smartRefresh;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.gxg.demo8.mydemo8.R;
import com.gxg.demo8.mydemo8.rxjava_retrofit_okhttp.bean.MovieSubject;

/**
 * 作者：Administrator on 2017/11/7 13:35
 * 邮箱：android_gaoxuge@163.com
 */
public class LocalImageHolderView implements Holder<MovieSubject.SubjectsBean.CastsBean> {

    private ImageView imageView;
    private TextView textView;
    @Override
    public View createView(Context context) {
        View view = View.inflate(context, R.layout.item_banner,null);
        imageView = (ImageView) view.findViewById(R.id.iv_banner);
        textView= (TextView) view.findViewById(R.id.tv_banner);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, MovieSubject.SubjectsBean.CastsBean data) {

        Glide.with(context).load(data.getAvatars().getLarge()).into(imageView);
        textView.setText(data.getName());
    }
}
