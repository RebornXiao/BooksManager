package com.book.booksmanager.net;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Httpmanage {

    private static Httpmanage instance;
    static {
        instance = new Httpmanage();
    }

    private Httpmanage(){
        mHandler = new Handler(Looper.getMainLooper());
        es = Executors.newFixedThreadPool(2);
    }

    private ExecutorService es;
    private Handler mHandler;

    public static Httpmanage getInstance() {
        if (instance == null) {
            synchronized (Httpmanage.class) {
                instance = new Httpmanage();
            }
        }
        return instance;
    }

    public void getBookListData(String label, final DataListener listener) {
        es.submit(new BookListNetWork(new BookListNetWork.NetListener() {
            @Override
            public void onGet(final String result) {
                postToMain(listener,result);

            }
        }, label));
    }

    public void getBookDetailData(String id, final DataListener listener) {
        es.submit(new BookDetailNetWork(new BookDetailNetWork.NetListener() {
            @Override
            public void onGet(String result) {
                postToMain(listener,result);
            }
        }, id));
    }

    public void searchBook(String isbn, final DataListener listener) {
        es.submit(new SearchNetWork(new SearchNetWork.NetListener() {
            @Override
            public void onGet(String result) {
                postToMain(listener,result);
            }
        }, isbn));
    }
    private void postToMain(final  DataListener listener , final String result ){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onData(result);
            }
        });
    }

    public void onStop() {
        es.shutdown();
    }

    public interface DataListener {
        void onData(String result);
    }

}
