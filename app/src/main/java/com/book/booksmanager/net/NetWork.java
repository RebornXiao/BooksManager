package com.book.booksmanager.net;

import com.book.booksmanager.config.Url;

/**
 * Created by baby on 2016/6/15.
 */
public class NetWork implements Runnable {
    private String lable;
    private NetListener listener;

    public NetWork(NetListener listener, String label) {
        this.listener = listener;
        this.lable = label;
    }

    @Override
    public void run() {
        String result = HttpUtils.doGet(Url.url + lable + Url.label);
        listener.onGet(result);
    }

    public interface NetListener {
        void onGet(String result);
    }

}
