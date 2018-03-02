package com.gxg.demo8.mydemo8.taobaoprogressbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gxg.demo8.mydemo8.R;

/**
 * 作者：Administrator on 2018/3/2 11:35
 * 邮箱：android_gaoxuge@163.com
 */
public class CustomProgressBarActivity extends AppCompatActivity {
    private CustomProgressBar mProgressBar ;
    private CustomProgressBar mProgressBar2 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_taobaoprogress);

        mProgressBar = (CustomProgressBar) findViewById(R.id.cpb_progresbar);
        mProgressBar.setOnFinishedListener(new CustomProgressBar.OnFinishedListener() {
            @Override
            public void onFinish() {
                Toast.makeText(CustomProgressBarActivity.this,"done!",Toast.LENGTH_SHORT).show();
            }
        });
        mProgressBar.setProgressDesc("剩余");
        mProgressBar.setMaxProgress(50);
        mProgressBar.setProgressColor(Color.parseColor("#F6CB82"));
        mProgressBar.setCurProgress(50);


        mProgressBar2 = (CustomProgressBar) findViewById(R.id.cpb_progresbar2);
        mProgressBar2.setOnAnimationEndListener(new CustomProgressBar.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                Toast.makeText(CustomProgressBarActivity.this,"animation end!",Toast.LENGTH_SHORT).show();
            }
        });
        mProgressBar2.setProgressDesc("剩余");
        mProgressBar2.setMaxProgress(100);
        mProgressBar2.setProgressColor(Color.parseColor("#79aa6b"));
        mProgressBar2.setCurProgress(80,4000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
