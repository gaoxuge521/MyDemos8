package com.gxg.demo8.mydemo8.tagcloudview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gxg.demo8.mydemo8.R;
import com.moxun.tagcloudlib.view.TagCloudView;

/**
 * 作者：Administrator on 2018/3/1 18:06
 * 邮箱：android_gaoxuge@163.com
 */
public class TagCloudViewActivity extends AppCompatActivity {
    private TagCloudView tagCloudView;
    private TextTagsAdapter textTagsAdapter;
    private ViewTagsAdapter viewTagsAdapter;
    private VectorTagsAdapter vectorTagsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtags);
        tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud);
        tagCloudView.setBackgroundColor(Color.LTGRAY);

        textTagsAdapter = new TextTagsAdapter(new String[20]);
        viewTagsAdapter = new ViewTagsAdapter();
        vectorTagsAdapter = new VectorTagsAdapter();

        tagCloudView.setAdapter(textTagsAdapter);

        findViewById(R.id.tag_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(textTagsAdapter);
            }
        });

        findViewById(R.id.tag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(viewTagsAdapter);
            }
        });

        findViewById(R.id.tag_vector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(vectorTagsAdapter);
            }
        });

        findViewById(R.id.test_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TagCloudViewActivity.this, FragmentTestActivity.class));
            }
        });
    }
}
