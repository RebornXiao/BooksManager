package com.book.booksmanager.net;

import com.book.booksmanager.config.Url;
import com.book.booksmanager.utils.MLog;

public class SearchNetWork implements Runnable {
    private String isbn;
    private NetListener listener;

    public SearchNetWork(NetListener listener, String isbn) {
        this.listener = listener;
        this.isbn = isbn;
    }

    @Override
    public void run() {
        String url = Url.isbnSearch + isbn;
        MLog.e("访问地址：" + url);
        String result = HttpUtils.doGet(url);
        listener.onGet(result);
    }

    public interface NetListener {
        void onGet(String result);
    }

}
