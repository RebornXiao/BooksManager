package com.book.booksmanager.net;

import com.book.booksmanager.config.Url;
import com.book.booksmanager.utils.MLog;

public class BookListNetWork implements Runnable {
    private String lable;
    private NetListener listener;

    public BookListNetWork(NetListener listener, String label) {
        this.listener = listener;
        this.lable = label;
    }

    @Override
    public void run() {
        String url = Url.search + lable + Url.label;
        MLog.e("访问地址:" + url);
        String result = HttpUtils.doGet(url);
        listener.onGet(result);
    }

    public interface NetListener {
        void onGet(String result);
    }

}
