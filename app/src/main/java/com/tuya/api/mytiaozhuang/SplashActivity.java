package com.tuya.api.mytiaozhuang;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuya.api.mytiaozhuang.guide.Guidee;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final String START_MAIN = "l";
    private ProgressBar mCustomProgressBar;

    static final String TAG = "SplashActivity";
    private RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        startAnim();
    }

    private void init() {

        TextView tv_version = findViewById(R.id.tv_version);
        mCustomProgressBar = (ProgressBar) findViewById(R.id.customProgressBar);
        // 自定义彩色进度条
        rlSplash = (RelativeLayout) findViewById(R.id.rl_splash);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            tv_version.setText("version:" + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("version");
        }
        //判断网络
        if (isNetworkConnected(this)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    isNetwork();
                }
            }).start();
        } else {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();

        }
    }
    /**
     * 启动动画
     */
    private void startAnim() {
        // 渐变动画,从完全透明到完全不透明
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        // 持续时间 2 秒
        alpha.setDuration(1000);
        // 动画结束后，保持动画状态
        alpha.setFillAfter(true);

        // 设置动画监听器
        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画结束时回调此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                // 跳转到下一个页面
           //     jumpNextPage();
            }
        });

        // 启动动画
        rlSplash.startAnimation(alpha);
    }


    //判断是否连接网络
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    private void goGuide() {
        Intent i = new Intent(SplashActivity.this, Guidee.class);
        startActivity(i);
        finish();
    }
    private void isNetwork() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(50);
                mCustomProgressBar.setProgress(i);
                Log.e(TAG, "isNetwork() " + i);
                boolean inStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
                if (inStartMain) {
                     //进入过
                    if (i == 99) {
                        Log.e(TAG, "isNetwork() called" + i);
 //发送intent实现页面跳转，第一个参数为当前页面的context，第二个参数为要跳转的主页
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } else {
                    //没进入过
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "跳转引导页" );
                            // 跳到引导页面
                            CacheUtils.setBoolean(SplashActivity.this,START_MAIN);
                            goGuide();
                        }
                    }).start();
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
