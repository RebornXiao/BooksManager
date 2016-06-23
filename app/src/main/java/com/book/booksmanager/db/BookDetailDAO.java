package com.book.booksmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.book.booksmanager.data.BookDBBean;
import com.book.booksmanager.utils.MLog;

import java.util.ArrayList;
import java.util.List;

public class BookDetailDAO {

    //CategoryDAO 表相关常量
    public static final String TB_BOOK_DETAIL = "tb_book_detail";
    public static final String NID = "id";
    public static final String BOOKID = "bookid";//类别
    public static final String SUBTITLE = "subtitle";//数量
    public static final String PUBDATE = "pubdate";//数量
    public static final String IMAGE = "image";//数量
    public static final String CATALOG = "catalog";//数量
    public static final String PAGES = "pages";//数量
    public static final String ALT = "alt";//数量
    public static final String PUBLISHER = "publisher";//数量
    public static final String ISBN10 = "isbn10";//数量
    public static final String TITLE = "title";//数量
    public static final String URL = "url";//数量
    public static final String AUTHOR_INTRO = "author_intro";//数量
    public static final String SUMMARY = "summary";//数量
    public static final String PRICE = "price";//数量
    public static final String AUTHOR = "author";//数量
    public static final String NUMRATERS = "numRaters";//数量
    public static final String AVERAGE = "average";//数量
    public static final String MAX = "max";//数量
    public static final String TAGS = "tags";//数量
    public static final String CATEGORY = "category";//本书在本地所属类别

    private static SQLiteDatabase db;
    MyDBHelper mOpenHelper;
    Context context;

    public BookDetailDAO(Context c) {
        this.context = c;
        mOpenHelper = new MyDBHelper(context);
        open();
    }

    /**
     * Open the database
     */
    public void open() throws SQLiteException {
        if (db != null)
            return;
        try {
            db = mOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = mOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /**
     * 将最新的通知数据插入到数据库
     *
     * @param list 从网络上获取的最新的通知集合
     * @return
     */
    public long insertBookFromNet(List<BookDBBean> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        int i = 0;
        for (; i < list.size(); i++) {
            insertBookDetail(list.get(i));
        }
        return i;
    }

    // 新增书籍类别
    public long insertBookDetail(BookDBBean c) {
        long num = -1;
        BookDBBean info = queryCourseByTitle(c.getTitle());
        if (info != null) {
            c.setId(info.getId());
            try {
                update(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ContentValues cv = new ContentValues();
            cv.put(BOOKID, c.getBookid());
            cv.put(SUBTITLE, c.getSubtitle());
            cv.put(PUBDATE, c.getPubdate());
            cv.put(IMAGE, c.getImage());
            cv.put(CATALOG, c.getCatalog());
            cv.put(PAGES, c.getPages());
            cv.put(ALT, c.getAlt());
            cv.put(PUBLISHER, c.getPublisher());
            cv.put(ISBN10, c.getIsbn10());
            cv.put(TITLE, c.getTitle());
            cv.put(URL, c.getUrl());
            cv.put(AUTHOR_INTRO, c.getAuthor_intro());
            cv.put(SUMMARY, c.getSummary());
            cv.put(PRICE, c.getPrice());
            cv.put(AUTHOR, c.getAuthor());
            cv.put(NUMRATERS, c.getNumRaters());
            cv.put(AVERAGE, c.getAverage());
            cv.put(MAX, c.getMax());
            cv.put(TAGS, c.getTags());
            cv.put(CATEGORY, c.getCategory());
            num = db.insert(TB_BOOK_DETAIL, null, cv);
        }
        return num;
    }

    /**
     * 更新数据
     *
     * @return
     */
    public int update(BookDBBean c) {
        int num = 0;
        if (c != null) {
            ContentValues cv = new ContentValues();
            cv.put(BOOKID, c.getBookid());
            cv.put(SUBTITLE, c.getSubtitle());
            cv.put(PUBDATE, c.getPubdate());
            cv.put(IMAGE, c.getImage());
            cv.put(CATALOG, c.getCatalog());
            cv.put(PAGES, c.getPages());
            cv.put(ALT, c.getAlt());
            cv.put(PUBLISHER, c.getPublisher());
            cv.put(ISBN10, c.getIsbn10());
            cv.put(TITLE, c.getTitle());
            cv.put(URL, c.getUrl());
            cv.put(AUTHOR_INTRO, c.getAuthor_intro());
            cv.put(SUMMARY, c.getSummary());
            cv.put(PRICE, c.getPrice());
            cv.put(AUTHOR, c.getAuthor());
            cv.put(NUMRATERS, c.getNumRaters());
            cv.put(AVERAGE, c.getAverage());
            cv.put(MAX, c.getMax());
            cv.put(TAGS, c.getTags());
            cv.put(CATEGORY, c.getCategory());
            String where = "id=" + c.getId();
            num = db.update(TB_BOOK_DETAIL, cv, where, null);
        }
        return num;
    }

    // 查询所有
    private Cursor queryAllBookFromDB() {
        String col[] = {NID, BOOKID, SUBTITLE, PUBDATE, IMAGE, CATALOG, PAGES, ALT, PUBLISHER, TITLE,
                ISBN10, URL, AUTHOR_INTRO, SUMMARY, PRICE, AUTHOR, NUMRATERS, AVERAGE, MAX, TAGS, CATEGORY};
        Cursor c = db.query(TB_BOOK_DETAIL, col, null, null, null,
                null, NID + " desc");
        return c;
    }

    /**
     * 获取所有书籍
     *
     * @return
     */
    public List<BookDBBean> getAllBookFromCursor() {
        Cursor c = queryAllBookFromDB();
        List<BookDBBean> list = null;
        if (c != null && c.getCount() > 0) {
            list = new ArrayList<BookDBBean>();
            while (c.moveToNext()) {
                BookDBBean info = new BookDBBean();
                info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                info.setSubtitle(c.getString(c.getColumnIndex(SUBTITLE)));
                info.setPubdate(c.getString(c.getColumnIndex(PUBDATE)));
                info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                info.setCatalog(c.getString(c.getColumnIndex(CATALOG)));
                info.setPages(c.getString(c.getColumnIndex(PAGES)));
                info.setAlt(c.getString(c.getColumnIndex(ALT)));
                info.setPublisher(c.getString(c.getColumnIndex(PUBLISHER)));
                info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                info.setIsbn10(c.getString(c.getColumnIndex(ISBN10)));
                info.setUrl(c.getString(c.getColumnIndex(URL)));
                info.setAuthor_intro(c.getString(c.getColumnIndex(AUTHOR_INTRO)));
                info.setSummary(c.getString(c.getColumnIndex(SUMMARY)));
                info.setPrice(c.getString(c.getColumnIndex(PRICE)));
                info.setAuthor(c.getString(c.getColumnIndex(AUTHOR)));
                info.setNumRaters(c.getString(c.getColumnIndex(NUMRATERS)));
                info.setAverage(c.getString(c.getColumnIndex(AVERAGE)));
                info.setMax(c.getString(c.getColumnIndex(MAX)));
                info.setTags(c.getString(c.getColumnIndex(TAGS)));
                info.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                list.add(info);
            }
        }
        if (c != null) {
            c.close();
        }
        return list;
    }

    /**
     * @return 根据类别查找 书籍
     */
    public List<BookDBBean> queryCourseByCategory(String category) {
        List<BookDBBean> list = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + TB_BOOK_DETAIL + " WHERE " + CATEGORY + " = ?", new String[]{category});
            if (c.getCount() > 0) {
                list = new ArrayList<BookDBBean>();
                while (c.moveToNext()) {
                    BookDBBean info = new BookDBBean();
                    info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                    info.setSubtitle(c.getString(c.getColumnIndex(SUBTITLE)));
                    info.setPubdate(c.getString(c.getColumnIndex(PUBDATE)));
                    info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                    info.setCatalog(c.getString(c.getColumnIndex(CATALOG)));
                    info.setPages(c.getString(c.getColumnIndex(PAGES)));
                    info.setAlt(c.getString(c.getColumnIndex(ALT)));
                    info.setPublisher(c.getString(c.getColumnIndex(PUBLISHER)));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                    info.setIsbn10(c.getString(c.getColumnIndex(ISBN10)));
                    info.setUrl(c.getString(c.getColumnIndex(URL)));
                    info.setAuthor_intro(c.getString(c.getColumnIndex(AUTHOR_INTRO)));
                    info.setSummary(c.getString(c.getColumnIndex(SUMMARY)));
                    info.setPrice(c.getString(c.getColumnIndex(PRICE)));
                    info.setAuthor(c.getString(c.getColumnIndex(AUTHOR)));
                    info.setNumRaters(c.getString(c.getColumnIndex(NUMRATERS)));
                    info.setAverage(c.getString(c.getColumnIndex(AVERAGE)));
                    info.setMax(c.getString(c.getColumnIndex(MAX)));
                    info.setTags(c.getString(c.getColumnIndex(TAGS)));
                    info.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                    list.add(info);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return list;
    }


    /**
     * @param id
     * @return
     */
    public BookDBBean queryCourseById(int id) {
        BookDBBean info = null;
        Cursor c = null;
        String str = "id=" + id;
        String col[] = {NID, BOOKID, SUBTITLE, PUBDATE, IMAGE,
                CATALOG, PAGES, ALT, PUBLISHER, TITLE, ISBN10, URL,
                AUTHOR_INTRO, SUMMARY, PRICE, AUTHOR, NUMRATERS, AVERAGE, MAX, TAGS, CATEGORY};
        try {
            c = db.query(TB_BOOK_DETAIL, col, str, null, null, null, null);
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    info = new BookDBBean();
                    info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                    info.setSubtitle(c.getString(c.getColumnIndex(SUBTITLE)));
                    info.setPubdate(c.getString(c.getColumnIndex(PUBDATE)));
                    info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                    info.setCatalog(c.getString(c.getColumnIndex(CATALOG)));
                    info.setPages(c.getString(c.getColumnIndex(PAGES)));
                    info.setAlt(c.getString(c.getColumnIndex(ALT)));
                    info.setPublisher(c.getString(c.getColumnIndex(PUBLISHER)));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                    info.setIsbn10(c.getString(c.getColumnIndex(ISBN10)));
                    info.setUrl(c.getString(c.getColumnIndex(URL)));
                    info.setAuthor_intro(c.getString(c.getColumnIndex(AUTHOR_INTRO)));
                    info.setSummary(c.getString(c.getColumnIndex(SUMMARY)));
                    info.setPrice(c.getString(c.getColumnIndex(PRICE)));
                    info.setAuthor(c.getString(c.getColumnIndex(AUTHOR)));
                    info.setNumRaters(c.getString(c.getColumnIndex(NUMRATERS)));
                    info.setAverage(c.getString(c.getColumnIndex(AVERAGE)));
                    info.setMax(c.getString(c.getColumnIndex(MAX)));
                    info.setTags(c.getString(c.getColumnIndex(TAGS)));
                    info.setCategory(c.getString(c.getColumnIndex(CATEGORY)));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return info;
    }

    /**
     * @return
     */
    public BookDBBean queryCourseByTitle(String title) {
        BookDBBean info = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + TB_BOOK_DETAIL + " WHERE " + TITLE + " = ?", new String[]{title});
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    info = new BookDBBean();
                    info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                    info.setSubtitle(c.getString(c.getColumnIndex(SUBTITLE)));
                    info.setPubdate(c.getString(c.getColumnIndex(PUBDATE)));
                    info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                    info.setCatalog(c.getString(c.getColumnIndex(CATALOG)));
                    info.setPages(c.getString(c.getColumnIndex(PAGES)));
                    info.setAlt(c.getString(c.getColumnIndex(ALT)));
                    info.setPublisher(c.getString(c.getColumnIndex(PUBLISHER)));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                    info.setIsbn10(c.getString(c.getColumnIndex(ISBN10)));
                    info.setUrl(c.getString(c.getColumnIndex(URL)));
                    info.setAuthor_intro(c.getString(c.getColumnIndex(AUTHOR_INTRO)));
                    info.setSummary(c.getString(c.getColumnIndex(SUMMARY)));
                    info.setPrice(c.getString(c.getColumnIndex(PRICE)));
                    info.setAuthor(c.getString(c.getColumnIndex(AUTHOR)));
                    info.setNumRaters(c.getString(c.getColumnIndex(NUMRATERS)));
                    info.setAverage(c.getString(c.getColumnIndex(AVERAGE)));
                    info.setMax(c.getString(c.getColumnIndex(MAX)));
                    info.setTags(c.getString(c.getColumnIndex(TAGS)));
                    info.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return info;
    }

    /**
     * @return 根据书名和类别找书
     */
    public BookDBBean queryCourseByTitle(String title, String category) {
        BookDBBean info = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + TB_BOOK_DETAIL + " WHERE " + TITLE + " = ? and " + CATEGORY + " = ? ", new String[]{title, category});
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    info = new BookDBBean();
                    info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                    info.setSubtitle(c.getString(c.getColumnIndex(SUBTITLE)));
                    info.setPubdate(c.getString(c.getColumnIndex(PUBDATE)));
                    info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                    info.setCatalog(c.getString(c.getColumnIndex(CATALOG)));
                    info.setPages(c.getString(c.getColumnIndex(PAGES)));
                    info.setAlt(c.getString(c.getColumnIndex(ALT)));
                    info.setPublisher(c.getString(c.getColumnIndex(PUBLISHER)));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                    info.setIsbn10(c.getString(c.getColumnIndex(ISBN10)));
                    info.setUrl(c.getString(c.getColumnIndex(URL)));
                    info.setAuthor_intro(c.getString(c.getColumnIndex(AUTHOR_INTRO)));
                    info.setSummary(c.getString(c.getColumnIndex(SUMMARY)));
                    info.setPrice(c.getString(c.getColumnIndex(PRICE)));
                    info.setAuthor(c.getString(c.getColumnIndex(AUTHOR)));
                    info.setNumRaters(c.getString(c.getColumnIndex(NUMRATERS)));
                    info.setAverage(c.getString(c.getColumnIndex(AVERAGE)));
                    info.setMax(c.getString(c.getColumnIndex(MAX)));
                    info.setTags(c.getString(c.getColumnIndex(TAGS)));
                    info.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return info;
    }


    /**
     * 删除全部
     */
    public void clearInfo() {
        try {
            db.delete(TB_BOOK_DETAIL, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一条
     */
    public void deleteInfo(String title) {
        Cursor c = null;
        try {
            BookDBBean info = queryCourseByTitle(title);
            if (info != null) {
                info.getId();
                String where = " id = " + info.getId();
                db.delete(TB_BOOK_DETAIL, where, null);
            } else {
                MLog.e("根本沒这本书哦~~~~");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
