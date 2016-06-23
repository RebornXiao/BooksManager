package com.book.booksmanager.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.book.booksmanager.MApp;
import com.book.booksmanager.utils.ActivityStackControlUtil;

/**
 * 基类
 */
public class BaseActivity extends AppCompatActivity {
    public MApp app;
    public Context mContext;
    public Handler uiHandler;
    public ActivityStackControlUtil activityUtil;
    private boolean isOnKeyBack;
    public ProgressDialog mProgressDialog;
    /**
     * 退出提示Toast
     */
    private Toast mExitToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        app = (MApp) getApplication();
        activityUtil = ActivityStackControlUtil.getInstance();
        activityUtil.onCreate(this);
        app.addActivates(this);
        uiHandler = new Handler(mContext.getMainLooper());

    }

    public void exitapplication() {
        if (isOnKeyBack) {
            uiHandler.removeCallbacks(onBackExitRunnable);
            if (mExitToast != null) {
                mExitToast.cancel();
            }
            /**直接退出*/
            app.exitApp();
        } else {
            isOnKeyBack = true;
            if (mExitToast == null) {
                mExitToast = Toast.makeText(mContext, "再按一次返回键退出应用", Toast.LENGTH_SHORT);
            }
            mExitToast.show();
            uiHandler.postDelayed(onBackExitRunnable, 2000);
        }
    }

    public void dismissTipsDialogs() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showTipsDialogs() {
        dismissTipsDialogs();
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("正在请求数据");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void showTipsDialogs(String msg) {
        dismissTipsDialogs();
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage(msg);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public Runnable onBackExitRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBack = false;
            if (mExitToast != null) {
                mExitToast.cancel();
            }
        }
    };
}
