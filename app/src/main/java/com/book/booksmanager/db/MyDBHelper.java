package com.book.booksmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.book.booksmanager.db.CategoryDAO.NUM;
import static com.book.booksmanager.db.CategoryDAO.TB_BOOK_CATEGORY;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "book_manager.db";
    public static final int DATABASE_VERSION = 3;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE " + TB_BOOK_CATEGORY + " (" +
                com.book.booksmanager.db.CategoryDAO.NID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                com.book.booksmanager.db.CategoryDAO.TITLE + "  String," +
                NUM + "  String );";

        String sql2 = "CREATE TABLE " + com.book.booksmanager.db.CategoryDetailDAO.TB_BOOK_CATEGORY_DETAIL + " (" +
                com.book.booksmanager.db.CategoryDetailDAO.NID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                com.book.booksmanager.db.CategoryDetailDAO.URL + "  String," +
                com.book.booksmanager.db.CategoryDetailDAO.IMAGE + "  String," +
                com.book.booksmanager.db.CategoryDetailDAO.BOOKID + "  String," +
                com.book.booksmanager.db.CategoryDetailDAO.TITLE + "  String," +
                com.book.booksmanager.db.CategoryDetailDAO.CATEGORY + "  String );";

        String sql3 = "CREATE TABLE " + com.book.booksmanager.db.BookDetailDAO.TB_BOOK_DETAIL + " (" +
                com.book.booksmanager.db.BookDetailDAO.NID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                com.book.booksmanager.db.BookDetailDAO.BOOKID + "  String," +
                com.book.booksmanager.db.BookDetailDAO.SUBTITLE + "  String," +
                com.book.booksmanager.db.BookDetailDAO.PUBDATE + "  String," +
                com.book.booksmanager.db.BookDetailDAO.IMAGE + "  String," +
                com.book.booksmanager.db.BookDetailDAO.CATALOG + "  String," +
                com.book.booksmanager.db.BookDetailDAO.PAGES + "  String," +
                com.book.booksmanager.db.BookDetailDAO.ALT + "  String," +
                com.book.booksmanager.db.BookDetailDAO.PUBLISHER + "  String," +
                com.book.booksmanager.db.BookDetailDAO.ISBN10 + "  String," +
                com.book.booksmanager.db.BookDetailDAO.TITLE + "  String," +
                com.book.booksmanager.db.BookDetailDAO.URL + "  String," +
                com.book.booksmanager.db.BookDetailDAO.AUTHOR_INTRO + "  String," +
                com.book.booksmanager.db.BookDetailDAO.SUMMARY + "  String," +
                com.book.booksmanager.db.BookDetailDAO.PRICE + "  String," +
                com.book.booksmanager.db.BookDetailDAO.AUTHOR + "  String," +
                com.book.booksmanager.db.BookDetailDAO.NUMRATERS + "  String," +
                com.book.booksmanager.db.BookDetailDAO.AVERAGE + "  String," +
                com.book.booksmanager.db.BookDetailDAO.MAX + "  String," +
                com.book.booksmanager.db.BookDetailDAO.TAGS + "  String," +
                com.book.booksmanager.db.BookDetailDAO.CATEGORY + "  String );";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

}
