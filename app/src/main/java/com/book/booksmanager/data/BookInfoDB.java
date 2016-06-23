package com.book.booksmanager.data;

/**
 * Created by HP-PC on 2016/6/19.
 */
public class BookInfoDB {
    private int id;

    private String url;
    private String image;
    private String bookid;
    private String title;

    private String category;

    public BookInfoDB() {
        super();
    }

    public BookInfoDB(BookInfo info, String category) {
        this.category = category;
        url = info.getUrl();
        image = info.getImage();
        bookid = info.getId();
        title = info.getTitle();
    }

    public BookInfoDB(BookDBBean bookDBBean) {
        url = bookDBBean.getUrl();
        image = bookDBBean.getImage();
        bookid = bookDBBean.getBookid();
        title = bookDBBean.getTitle();
        category = bookDBBean.getCategory();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
