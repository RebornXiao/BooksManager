package com.book.booksmanager.config;

public class Url {
    public static final String url = "https://api.douban.com/v2/book/";

    public static final String search = url + "search?q=";

    public static final String label = "&fields=id,title,url,image&count=100";

    //根据isbn查找
    //https://api.douban.com/v2/book/isbn/9787530641781
    public static final String isbnSearch = url + "/isbn/";


//    https://api.douban.com/v2/book/search?q=%E5%A4%96%E5%9B%BD%E6%96%87%E5%AD%A6&fields=id,title,url,image
}
