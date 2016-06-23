package com.book.booksmanager.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.book.booksmanager.R;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 欢迎页面
 */
public class LoadActivity extends BaseActivity {

    @Bind(R.id.iv_splash)
    ImageView ivSplash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);

        AssetManager assets = getAssets();
        InputStream is = null;
        try {
            is = assets.open("images/splash.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        ivSplash.setImageBitmap(bitmap);
        //跳转进引导页面
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activityUtil.jumpTo(GuideActivity.class);
                finish();
            }
        }, 500);
    }

}
