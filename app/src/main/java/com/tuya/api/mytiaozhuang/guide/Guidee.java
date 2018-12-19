package com.tuya.api.mytiaozhuang.guide;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import java.util.ArrayList;
import com.tuya.api.mytiaozhuang.MainActivity;
import com.tuya.api.mytiaozhuang.R;

import java.util.List;

public class Guidee extends Activity implements ViewPager.OnPageChangeListener {
    //引导页面
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = { R.id.iv1, R.id.iv2, R.id.iv3 };
    private Button start_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//全屏化
        setContentView(R.layout.activity_guidee);
        initViews();//初始化View
        initDots();//初始化滑动圆点
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.one, null));
        views.add(inflater.inflate(R.layout.two, null));
        views.add(inflater.inflate(R.layout.three, null));

        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        start_btn = (Button) views.get(2).findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Guidee.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        vp.addOnPageChangeListener(this);
    }

    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < ids.length; i++) {
            if (arg0 == i) {
                dots[i].setImageResource(R.drawable.login_point_selected);
            } else {
                dots[i].setImageResource(R.drawable.login_point);
            }
        }
    }
}
