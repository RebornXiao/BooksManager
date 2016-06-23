package com.book.booksmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.book.booksmanager.data.CategoryInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoryDAO {

    //CategoryDAO 表相关常量
    public static final String TB_BOOK_CATEGORY = "tb_book_category";
    public static final String NID = "id";
    public static final String TITLE = "title";//类别
    public static final String NUM = "num";//数量

    private static SQLiteDatabase db;
    MyDBHelper mOpenHelper;
    Context context;

    public CategoryDAO(Context c) {
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
    public long insertCategoryFromNet(List<CategoryInfo> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        int i = 0;
        for (; i < list.size(); i++) {
            insertCategory(list.get(i));
        }
        return i;
    }

    // 新增书籍类别
    public long insertCategory(CategoryInfo c) {
        long num = -1;
        CategoryInfo info = queryCourseByTitle(c.getTitle());
        if (info != null) {
            c.setId(info.getId());
            try {
                update(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ContentValues cv = new ContentValues();
            cv.put(TITLE, c.getTitle());
            cv.put(NUM, c.getNums());
            num = db.insert(TB_BOOK_CATEGORY, null, cv);
        }
        return num;
    }

    /**
     * 更新数据
     *
     * @return
     */
    public int update(CategoryInfo info) {
        int num = 0;
        if (info != null) {
            ContentValues cv = new ContentValues();
            cv.put(TITLE, info.getTitle());
            cv.put(NUM, info.getNums());
            String where = "id=" + info.getId();
            num = db.update(TB_BOOK_CATEGORY, cv, where, null);
        }
        return num;
    }

    // 查询所有通知
    private Cursor queryAllNoticesFromDB() {
        String col[] = {NID, TITLE, NUM};
        Cursor c = db.query(TB_BOOK_CATEGORY, col, null, null, null,
                null, NID + " desc");
        return c;
    }

    /**
     * 获取所有通知记录
     *
     * @return
     */
    public List<CategoryInfo> getAllCategoryFromCursor() {
        Cursor c = queryAllNoticesFromDB();
        List<CategoryInfo> list = null;
        if (c != null && c.getCount() > 0) {
            list = new ArrayList<CategoryInfo>();
            while (c.moveToNext()) {
                CategoryInfo info = new CategoryInfo();
                info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                info.setNums(c.getString(c.getColumnIndex(NUM)));
                list.add(info);
            }
        }
        if (c != null) {
            c.close();
        }
        if (list != null && list.size() > 2) {
            sortData(list);
        }
        return list;
    }

    private void sortData(List<CategoryInfo> list) {
        for (int i = 0, size = list.size(); i < size; i++) {
            Collections.sort(list, new Comparator<CategoryInfo>() {
                @Override
                public int compare(CategoryInfo lhs, CategoryInfo rhs) {
                    double d2 = Integer.parseInt(lhs.getNums());
                    double d1 = Integer.parseInt(rhs.getNums());
                    return new Double(d1).compareTo(d2);
                }
            });
        }
    }

    /**
     * @param id
     * @return
     */
    public CategoryInfo queryCourseById(int id) {
        CategoryInfo info = null;
        Cursor c = null;
        String str = "id=" + id;
        String col[] = {NID, TITLE, NUM};
        try {
            c = db.query(TB_BOOK_CATEGORY, col, str, null, null, null, null);
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    info = new CategoryInfo();
                    info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                    info.setNums(c.getString(c.getColumnIndex(NUM)));
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
    public CategoryInfo queryCourseByTitle(String title) {
        CategoryInfo info = null;
        Cursor c = null;
        try {
            c = db.rawQuery("select * from " + TB_BOOK_CATEGORY + " WHERE " + TITLE + " = ?", new String[]{title});
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    info = new CategoryInfo();
                    info.setId(Integer.parseInt(c.getString(c.getColumnIndex(NID))));
                    info.setTitle(c.getString(c.getColumnIndex(TITLE)));
                    info.setNums(c.getString(c.getColumnIndex(NUM)));
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
            db.delete(TB_BOOK_CATEGORY, null, null);
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
            CategoryInfo info = queryCourseByTitle(title);
            if (info != null) {
                info.getId();
                String where = " id = " + info.getId();
                db.delete(TB_BOOK_CATEGORY, where, null);
            }
//            c = db.delete("delete  from " + TB_BOOK_CATEGORY + " WHERE " + TITLE + " = ?", new String[]{title});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
