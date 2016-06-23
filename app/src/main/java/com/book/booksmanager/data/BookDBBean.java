package com.book.booksmanager.data;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by baby on 2016/6/18.
 */
public class BookDBBean implements Serializable {
    private int id;

    private String category;//类别

    private String bookid;

    private String subtitle;//书名副标题
    private String pubdate;//出版日期
    private String image;

    private String catalog;//关键字
    private String pages;//页数
    private String alt;//书本网址
    private String publisher;//出版社
    private String isbn10;//

    private String title;//标题
    private String url;//访问地址
    private String author_intro;//作者简介
    private String summary;//作品简介
    private String price;//价格
    private String author;//作者

    private String numRaters;//评价人数
    private String average;//平均得分
    private String max;//最大的分

    private String tags;//标签

    public BookDBBean() {
        super();
    }

    public BookDBBean(BookDetail bookDetail, String category) {
        StringBuffer sb = new StringBuffer();
        List<BookDetail.TagsBean> tagsBeen = bookDetail.getTags();
        for (int i = 0, size = tagsBeen.size(); i < size; i++) {
            if (i != size - 1) {
                sb.append(tagsBeen.get(i).getTitle() + ",");
            } else {
                sb.append(tagsBeen.get(i).getTitle());
            }
        }
        tags = sb.toString();
        if (!TextUtils.isEmpty(category)) {
            this.category = category;
        } else {
            if (tagsBeen != null && tagsBeen.size() > 0) {
                this.category = tagsBeen.get(0).getTitle();
            } else {
                this.category = "未分类书籍";
            }
        }
        bookid = bookDetail.getId();
        subtitle = bookDetail.getSubtitle();
        pubdate = bookDetail.getPubdate();
        image = bookDetail.getImage();
        catalog = bookDetail.getCatalog();

        pages = bookDetail.getPages();
        alt = bookDetail.getAlt();
        publisher = bookDetail.getPublisher();
        isbn10 = bookDetail.getIsbn10();
        title = bookDetail.getTitle();
        url = bookDetail.getUrl();
        author = bookDetail.getAuthor().get(0);
        author_intro = bookDetail.getAuthor_intro();
        summary = bookDetail.getSummary();
        price = bookDetail.getPrice();
        numRaters = bookDetail.getRating().getNumRaters();
        average = bookDetail.getRating().getAverage();
        max = bookDetail.getRating().getMax();


    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getNumRaters() {
        return numRaters;
    }

    public void setNumRaters(String numRaters) {
        this.numRaters = numRaters;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookDBBean{" +
                "id=" + id +
                ", bookid='" + bookid + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", image='" + image + '\'' +
                ", catalog='" + catalog + '\'' +
                ", pages='" + pages + '\'' +
                ", alt='" + alt + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", author_intro='" + author_intro + '\'' +
                ", summary='" + summary + '\'' +
                ", price='" + price + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
