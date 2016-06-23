package com.book.booksmanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

import com.book.booksmanager.data.BookDBBean;
import com.book.booksmanager.data.BookInfoDB;
import com.book.booksmanager.data.CategoryInfo;
import com.book.booksmanager.db.BookDetailDAO;
import com.book.booksmanager.db.CategoryDAO;
import com.book.booksmanager.db.CategoryDetailDAO;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class MApp extends Application {
    private List<SoftReference<Activity>> activitys = new ArrayList<SoftReference<Activity>>();
    public CategoryDAO categoryDAO;
    public BookDetailDAO bookDetailDAO;
    public CategoryDetailDAO categoryDetailDAO;


    public void exitApp() {
        for (SoftReference<Activity> activity : activitys) {
            Activity temp;
            if ((temp = activity.get()) != null) {
                temp.finish();
            }
        }
        onTerminate();
    }

    public void addActivates(Activity activity) {
        activitys.add(new SoftReference<>(activity));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        categoryDAO = new CategoryDAO(this);
        bookDetailDAO = new BookDetailDAO(this);
        categoryDetailDAO = new CategoryDetailDAO(this);
    }

    @SuppressWarnings("deprecation")
    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            NetworkInfo info = manager.getNetworkInfo(manager.getActiveNetwork());
            return info != null && info.isConnected();
        } else {
            NetworkInfo mobileInfo = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiInfo = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return (mobileInfo != null && mobileInfo.isConnected())
                    || (wifiInfo != null && wifiInfo.isConnected());
        }
    }

    private List<CategoryInfo> categoryInfos;

    public List<CategoryInfo> getCategoryInfos() {
        if (categoryInfos == null) {
            categoryInfos = categoryDAO.getAllCategoryFromCursor();
        }
        return categoryInfos;
    }

    public void savaCate(CategoryInfo info) {
        if (info != null) {
            categoryDAO.insertCategory(info);
        }
        categoryInfos = categoryDAO.getAllCategoryFromCursor();
    }

    public void deleteCategory(String title) {
        if (!TextUtils.isEmpty(title)) {
            categoryDAO.deleteInfo(title);
        }
        categoryInfos = categoryDAO.getAllCategoryFromCursor();
    }

    private List<BookDBBean> bookDBBeanList;

//    public List<BookDBBean> getBookInfos() {
//        if (bookDBBeanList == null) {
//            bookDBBeanList = bookDetailDAO.getAllBookFromCursor();
//        }
//        return bookDBBeanList;
//    }

    /**
     * 查找该类别所有书籍
     *
     * @return
     */
    public List<BookDBBean> getBookInfosByCategory(String category) {
        if (bookDBBeanList == null) {
            bookDBBeanList = bookDetailDAO.queryCourseByCategory(category);
        }
        return bookDBBeanList;
    }

    public void savaBook(BookDBBean info) {
        if (info != null) {
            bookDetailDAO.insertBookDetail(info);
        }
//        bookDBBeanList = bookDetailDAO.getAllBookFromCursor();
    }

    public void getBook(BookDBBean info) {
        if (info != null) {
            bookDetailDAO.insertBookDetail(info);
        }
//        bookDBBeanList = bookDetailDAO.getAllBookFromCursor();
    }

    public void savaBooks(List<BookDBBean> info) {
        if (info != null) {
            bookDetailDAO.insertBookFromNet(info);
        }
        bookDBBeanList = bookDetailDAO.getAllBookFromCursor();
    }

    public void deleteBook(String title) {
        if (!TextUtils.isEmpty(title)) {
            bookDetailDAO.deleteInfo(title);
        }
        bookDBBeanList = bookDetailDAO.getAllBookFromCursor();
    }

    private List<BookInfoDB> bookInfoDBs;

    /**
     * 保存所有书籍书籍类别简略信息
     *
     * @param info
     */

    public void savaBookInfoDbs(List<BookInfoDB> info) {
        if (info != null) {
            categoryDetailDAO.insertCategoryDetailFromNet(info);
        }
        bookInfoDBs = categoryDetailDAO.getAllCategoryDetailFromCursor();
    }
}
