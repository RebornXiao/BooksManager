package com.book.booksmanager.activity;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.book.booksmanager.R;
import com.book.booksmanager.data.CategoryInfo;
import com.book.booksmanager.utils.ScreenUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新手引导
 */
public class GuideActivity extends BaseActivity {
    @Bind(R.id.vp_guide)
    ViewPager vpGuide;
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.ll_point_group)
    LinearLayout llPointGroup;
    @Bind(R.id.view_red_point)
    View viewRedPoint;
    private ArrayList<ImageView> mImageViewList;
    private int mPointWidth;// 圆点间的距离
    private ArrayList<Bitmap> bitmapList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViews();
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.addOnPageChangeListener(new GuidePageListener());
    }

    @OnClick(R.id.btn_start)
    void start() {
        // 跳转主页面
        activityUtil.jumpTo(MainActivity.class);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (app.categoryDAO != null && app.categoryDAO.getAllCategoryFromCursor() != null && app.categoryDAO.getAllCategoryFromCursor().size() > 0) {


        } else {
            app.categoryDAO.clearInfo();
            List<CategoryInfo> categoryInfos = new ArrayList<>();
            CategoryInfo categoryInfo = new CategoryInfo("100", "外国文学");
            CategoryInfo categoryInfo1 = new CategoryInfo("100", "软件设计");
            CategoryInfo categoryInfo2 = new CategoryInfo("100", "工业技术");
            CategoryInfo categoryInfo3 = new CategoryInfo("100", "数据库");
            CategoryInfo categoryInfo4 = new CategoryInfo("100", "中国文学");
            CategoryInfo categoryInfo5 = new CategoryInfo("100", "侦探小说");
            CategoryInfo categoryInfo6 = new CategoryInfo("100", "java");
            CategoryInfo categoryInfo7 = new CategoryInfo("100", "python");
            CategoryInfo categoryInfo8 = new CategoryInfo("100", "android");
            CategoryInfo categoryInfo9 = new CategoryInfo("100", "Web");
            CategoryInfo categoryInfo10 = new CategoryInfo("100", "ios");
            CategoryInfo categoryInfo11 = new CategoryInfo("100", "php");
            CategoryInfo categoryInfo12 = new CategoryInfo("100", "c语言");
            CategoryInfo categoryInfo13 = new CategoryInfo("100", ".net");
            categoryInfos.add(categoryInfo);
            categoryInfos.add(categoryInfo1);
            categoryInfos.add(categoryInfo2);
            categoryInfos.add(categoryInfo3);
            categoryInfos.add(categoryInfo4);
            categoryInfos.add(categoryInfo5);
            categoryInfos.add(categoryInfo6);
            categoryInfos.add(categoryInfo7);
            categoryInfos.add(categoryInfo8);
            categoryInfos.add(categoryInfo9);
            categoryInfos.add(categoryInfo10);
            categoryInfos.add(categoryInfo11);
            categoryInfos.add(categoryInfo12);
            categoryInfos.add(categoryInfo13);
            app.categoryDAO.insertCategoryFromNet(categoryInfos);
        }
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        if (bitmapList == null) {
            bitmapList = new ArrayList<>();
        }
        bitmapList.clear();
        AssetManager assets = getAssets();
        InputStream is = null;
        try {
            is = assets.open("images/guide_1.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

        bitmapList.add(bitmap);

        try {
            is = assets.open("images/guide_2.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap2 = BitmapFactory.decodeStream(is, null, options);
        bitmapList.add(bitmap2);

        try {
            is = assets.open("images/guide_3.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap3 = BitmapFactory.decodeStream(is, null, options);
        bitmapList.add(bitmap3);

        mImageViewList = new ArrayList<ImageView>();
        // 初始化引导页的3个页面
        for (int i = 0; i < bitmapList.size(); i++) {
            ImageView image = new ImageView(this);
//            image.setBackgroundResource(mImageIds[i]);// 设置引导页背景
            image.setImageBitmap(bitmapList.get(i));
            mImageViewList.add(image);
        }

        // 初始化引导页的小圆点
        for (int i = 0; i < bitmapList.size(); i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);// 设置引导页默认圆点

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ScreenUtils.dp2px(this, 10), ScreenUtils.dp2px(this, 10));
            if (i > 0) {
                params.leftMargin = ScreenUtils.dp2px(this, 10);// 设置圆点间隔
            }
            point.setLayoutParams(params);// 设置圆点的大小
            llPointGroup.addView(point);// 将圆点添加给线性布局
        }
        // 获取视图树, 对layout结束事件进行监听
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    // 当layout执行结束后回调此方法
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        if (llPointGroup != null) {
                            llPointGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            mPointWidth = llPointGroup.getChildAt(1).getLeft()
                                    - llPointGroup.getChildAt(0).getLeft();
                        }
                    }
                });
    }

    /**
     * ViewPager数据适配器
     */
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bitmapList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * viewpager的滑动监听
     */
    class GuidePageListener implements OnPageChangeListener {

        // 滑动事件
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // System.out.println("当前位置:" + position + ";百分比:" + positionOffset
            // + ";移动距离:" + positionOffsetPixels);
            int len = (int) (mPointWidth * positionOffset) + position
                    * mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint
                    .getLayoutParams();// 获取当前红点的布局参数
            params.leftMargin = len;// 设置左边距
            viewRedPoint.setLayoutParams(params);// 重新给小红点设置布局参数
        }

        // 某个页面被选中
        @Override
        public void onPageSelected(int position) {
            if (position == bitmapList.size() - 1) {// 最后一个页面
                btnStart.setVisibility(View.VISIBLE);// 显示开始体验的按钮
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

        // 滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
