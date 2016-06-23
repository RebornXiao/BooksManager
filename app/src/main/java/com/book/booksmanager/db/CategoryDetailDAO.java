package com.book.booksmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.book.booksmanager.data.BookInfoDB;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailDAO {

    //CategoryDAO 表相关常量
    public static final String TB_BOOK_CATEGORY_DETAIL = "tb_book_category_detail";
    public static final String NID = "id";
    public static final String URL = "url";//类别
    public static final String IMAGE = "image";//类别
    public static final String BOOKID = "bookid";//类别
    public static final String TITLE = "title";//类别
    public static final String CATEGORY = "category";//


    private static SQLiteDatabase db;
    MyDBHelper mOpenHelper;
    Context context;

    public CategoryDetailDAO(Context c) {
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
     *
     * @return
     */
    public long insertCategoryDetailFromNet(List<BookInfoDB> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        int i = 0;
        for (; i < list.size(); i++) {
            insertCategoryDetail(list.get(i));
        }
        return i;
    }

    // 新增书籍类别
    public long insertCategoryDetail(BookInfoDB c) {
        long num = -1;
        BookInfoDB info = queryCourseByTitle(c.getTitle());
        if (info != null) {
            c.setId(info.getId());
            try {
                update(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ContentValues cv = new ContentValues();
            cv.put(URL, c.getTitle());
            cv.put(IMAGE, c.getImage());
            cv.put(BOOKID, c.getBookid());
            cv.put(TITLE, c.getTitle());
            cv.put(CATEGORY, c.getCategory());
            num = db.insert(TB_BOOK_CATEGORY_DETAIL, null, cv);
        }
        return num;
    }

    /**
     * 更新数据
     *
     * @return
     */
    public int update(BookInfoDB c) {
        int num = 0;
        if (c != null) {
            ContentValues cv = new ContentValues();
            cv.put(URL, c.getTitle());
            cv.put(IMAGE, c.getImage());
            cv.put(BOOKID, c.getBookid());
            cv.put(TITLE, c.getTitle());
            cv.put(CATEGORY, c.getCategory());
            String where = "id=" + c.getId();
            num = db.update(TB_BOOK_CATEGORY_DETAIL, cv, where, null);
        }
        return num;
    }

    private Cursor queryAllNoticesDetailFromDB() {
        String col[] = {NID, URL, IMAGE, BOOKID, TITLE, CATEGORY};
        Cursor c = db.query(TB_BOOK_CATEGORY_DETAIL, col, null, null, null,
                null, NID + " desc");
        return c;
    }

    /**
     *
     * @return
     */
    public List<BookInfoDB> getAllCategoryDetailFromCursor() {
        Cursor c = queryAllNoticesDetailFromDB();
        List<BookInfoDB> list = null;
        if (c != null && c.getCount() > 0) {
            list = new ArrayList<BookInfoDB>();
            while (c.moveToNext()) {
                BookInfoDB info = new BookInfoDB();
                info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                info.setUrl(c.getString(c.getColumnIndex(URL)));
                info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                info.setTitle(c.getString(c.getColumnIndex(TITLE)));
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
     * @param id
     * @return
     */
    public BookInfoDB queryCourseById(int id) {
        BookInfoDB info = null;
        Cursor c = null;
        String str = "id=" + id;
        String col[] = {NID, URL, IMAGE, BOOKID, TITLE, CATEGORY};
        try {
            c = db.query(TB_BOOK_CATEGORY_DETAIL, col, str, null, null, null, null);
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    info = new BookInfoDB();
                    info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                    info.setUrl(c.getString(c.getColumnIndex(URL)));
                    info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                    info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
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
    public BookInfoDB queryCourseByTitle(String title) {
        BookInfoDB info = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + TB_BOOK_CATEGORY_DETAIL + " WHERE " + TITLE + " = ?", new String[]{title});
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    info = new BookInfoDB();
                    info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                    info.setUrl(c.getString(c.getColumnIndex(URL)));
                    info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                    info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
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
    public List<BookInfoDB>  queryBookByCategory(String category) {
        List<BookInfoDB> list = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + TB_BOOK_CATEGORY_DETAIL + " WHERE " + CATEGORY + " = ?", new String[]{category});
            if (c.getCount() > 0) {
                list = new ArrayList<BookInfoDB>();
                while (c.moveToNext()) {
                    BookInfoDB info = new BookInfoDB();
                    info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                    info.setUrl(c.getString(c.getColumnIndex(URL)));
                    info.setImage(c.getString(c.getColumnIndex(IMAGE)));
                    info.setBookid(c.getString(c.getColumnIndex(BOOKID)));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
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
     * 删除全部
     */
    public void clearInfo() {
        try {
            db.delete(TB_BOOK_CATEGORY_DETAIL, null, null);
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
            BookInfoDB info = queryCourseByTitle(title);
            if (info != null) {
                info.getId();
                String where = " id = " + info.getId();
                db.delete(TB_BOOK_CATEGORY_DETAIL, where, null);
            }
//            c = db.delete("delete  from " + TB_BOOK_CATEGORY + " WHERE " + TITLE + " = ?", new String[]{title});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
