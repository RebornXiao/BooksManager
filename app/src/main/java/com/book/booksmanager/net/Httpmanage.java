package com.book.booksmanager.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Httpmanage {

    private static Httpmanage instance;

    private DataListener listener;

    private ExecutorService es = Executors.newFixedThreadPool(2);

    public static Httpmanage getInstance() {
        if (instance == null) {
            synchronized (Httpmanage.class) {
                instance = new Httpmanage();
            }
        }
        return instance;
    }

    public void getBookListData(String label, final DataListener listener) {
        this.listener = listener;
        es.submit(new BookListNetWork(new BookListNetWork.NetListener() {
            @Override
            public void onGet(String result) {
                listener.onData(result);
            }
        }, label));
    }
    public void getBookDetailData(String id, final DataListener listener) {
        this.listener = listener;
        es.submit(new BookDetailNetWork(new BookDetailNetWork.NetListener() {
            @Override
            public void onGet(String result) {
                listener.onData(result);
            }
        }, id));
    }
    public void searchBook(String isbn, final DataListener listener) {
        this.listener = listener;
        es.submit(new SearchNetWork(new SearchNetWork.NetListener() {
            @Override
            public void onGet(String result) {
                listener.onData(result);
            }
        }, isbn));
    }


    public void onStop() {
        es.shutdown();
    }

    public interface DataListener {
        void onData(String result);
    }

}
