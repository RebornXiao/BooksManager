package com.book.booksmanager.net;

import com.book.booksmanager.config.Url;
import com.book.booksmanager.utils.MLog;

public class BookDetailNetWork implements Runnable {
    private String id;
    private NetListener listener;

    public BookDetailNetWork(NetListener listener, String id) {
        this.listener = listener;
        this.id = id;
    }

    @Override
    public void run() {
        String url = Url.url + id;
        MLog.e("访问地址：" + url);
        String result = HttpUtils.doGet(url);
        listener.onGet(result);
    }

    public interface NetListener {
        void onGet(String result);
    }

}
